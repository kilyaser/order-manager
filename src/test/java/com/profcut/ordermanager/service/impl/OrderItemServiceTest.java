package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.OrderItemCreateMapper;
import com.profcut.ordermanager.controllers.rest.mapper.UpdateItemMapper;
import com.profcut.ordermanager.domain.dto.order.AddOrderItemsRequest;
import com.profcut.ordermanager.domain.dto.order.OrderItemFieldsPatch;
import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.entities.ProductEntity;
import com.profcut.ordermanager.domain.exceptions.OrderItemNotFoundException;
import com.profcut.ordermanager.domain.repository.OrderItemRepository;
import com.profcut.ordermanager.service.MaterialService;
import com.profcut.ordermanager.service.ProductService;
import com.profcut.ordermanager.service.TechnologistService;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.buildDefaultOrder;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultOrderItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {

    @Mock
    OrderItemRepository orderItemRepository;
    @Mock
    ProductService productService;
    @Mock
    MaterialService materialService;
    @Mock
    TechnologistService technologistService;
    @Mock
    OrderItemCreateMapper orderItemCreateMapper;
    @Spy
    UpdateItemMapper updateItemMapper = Mappers.getMapper(UpdateItemMapper.class);
    @InjectMocks
    OrderItemServiceImpl orderItemService;

    @Test
    @DisplayName("Получение позиции закзаа по id")
    void findById() {
        var item = TestDataHelper.getDefaultOrderItem();

        when(orderItemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        assertThatCode(() -> {
            var result = orderItemService.findById(item.getId());
            assertNotNull(result);
        }).doesNotThrowAnyException();

        verify(orderItemRepository).findById(item.getId());
    }

    @Test
    @DisplayName("Получение позиции закзаа по id. Ошибка получения.")
    void findById_exception() {
        var item = TestDataHelper.getDefaultOrderItem();

        when(orderItemRepository.findById(item.getId())).thenReturn(Optional.empty());

        assertThatCode(() -> orderItemService.findById(item.getId())).isInstanceOf(OrderItemNotFoundException.class);

        verify(orderItemRepository).findById(item.getId());
    }

    @Test
    @DisplayName("Добавление позиции в заказ")
    void createOrderItems() {
        var addItemReq = new AddOrderItemsRequest()
                .setOrderId(UUID.randomUUID())
                .setItemsRequest(List.of(
                        OrderItemRequest.builder()
                                .productId(UUID.randomUUID())
                                .quantity(10)
                                .build()
                ));
        var item = getDefaultOrderItem();
        var product = new ProductEntity().setProductId(UUID.randomUUID());

        when(orderItemCreateMapper.apply(any())).thenReturn(item);
        when(productService.getProductById(any())).thenReturn(product);


        assertThatCode(() -> orderItemService.createOrderItems(addItemReq.getItemsRequest())).doesNotThrowAnyException();

        verify(orderItemCreateMapper).apply(any());
        verify(productService).getProductById(any());
        verify(technologistService, never()).getById(any());
        verify(materialService, never()).findById(any());
    }

    @Test
    @DisplayName("Обновление позиции заказа")
    void updateOrderItem() {
        var itemEntity = TestDataHelper.getDefaultOrderItem();
        var productEntity = new ProductEntity()
                .setProductId(UUID.randomUUID())
                .setProductName("Сталь");
        var patch = new OrderItemFieldsPatch()
                .setItemId(itemEntity.getId())
                .setProductId(UUID.randomUUID())
                .setQuantity(10)
                .setPricePerProduct(BigDecimal.valueOf(5000));
        var captor = ArgumentCaptor.forClass(OrderItemEntity.class);

        when(orderItemRepository.findById(any())).thenReturn(Optional.of(itemEntity));
        when(productService.getProductById(patch.getProductId())).thenReturn(productEntity);

        assertEquals(itemEntity.getTotalPrice(), BigDecimal.valueOf(6000));
        assertThatCode(() -> orderItemService.updateOrderItem(patch)).doesNotThrowAnyException();

        verify(orderItemRepository).save(captor.capture());

        assertThat(captor.getValue())
                .isInstanceOf(OrderItemEntity.class)
                .satisfies(item -> {
                      assertThat(item.getProduct()).isEqualTo(productEntity);
                      assertThat(item.getPricePerProduct()).isEqualTo(patch.getPricePerProduct());
                      assertThat(item.getQuantity()).isEqualTo(patch.getQuantity());
                      assertThat(item.getTotalPrice()).isEqualTo(BigDecimal.valueOf(50000));
                });
    }

    @Test
    @DisplayName("Удаление позиций заказа по списку id")
    void deleteOrderItems() {
        var ids = Set.of(UUID.randomUUID(), UUID.randomUUID());

        assertThatCode(() -> orderItemService.deleteOrderItems(ids)).doesNotThrowAnyException();

        verify(orderItemRepository).deleteOrderItems(ids);
    }

    @Test
    @DisplayName("Удаление позиции по id")
    void deleteById() {
        var id = UUID.randomUUID();

        assertThatCode(() -> orderItemService.deleteById(id)).doesNotThrowAnyException();

        verify(orderItemRepository).deleteById(id);
    }
}
