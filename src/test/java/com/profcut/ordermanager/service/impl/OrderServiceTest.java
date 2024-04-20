package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.OrderItemCreateMapper;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.entities.ProductEntity;
import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import com.profcut.ordermanager.domain.exceptions.OrderNotFoundException;
import com.profcut.ordermanager.domain.repository.OrderRepository;
import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import com.profcut.ordermanager.service.CounterpartyService;
import com.profcut.ordermanager.service.ProductService;
import com.profcut.ordermanager.service.TechnologistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.buildDefaultOrder;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultCreateOrderRequest;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultOrderItem;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderItemCreateMapper orderItemCreateMapper;
    @Mock
    CurrentUserSecurityService currentUserSecurityService;
    @Mock
    TechnologistService technologistService;
    @Mock
    ProductService productService;
    @Mock
    CounterpartyService counterpartyService;
    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    @DisplayName("Успешное создание заказа")
    void createOrder() {
        var request = getDefaultCreateOrderRequest();

        when(counterpartyService.findById(any()))
                .thenReturn(new CounterpartyEntity().setId(UUID.randomUUID()).setName("name"));
        when(currentUserSecurityService.getLogin()).thenReturn("user@mail.ru");
        when(orderItemCreateMapper.apply(any())).thenReturn(getDefaultOrderItem());
        when(technologistService.getById(any())).thenReturn(new TechnologistEntity());
        when(productService.getProductById(any())).thenReturn(new ProductEntity());

        assertThatCode(() -> orderService.createOrder(request)).doesNotThrowAnyException();

        verify(counterpartyService).findById(any(UUID.class));
        verify(currentUserSecurityService).getLogin();
        verify(orderItemCreateMapper).apply(any());
        verify(technologistService).getById(any(UUID.class));
        verify(productService).getProductById(any(UUID.class));
        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    @DisplayName("Получение страницы заказа")
    void getOrdersPage() {
        var request = new PageRequest().setPage(0).setSize(20);

        assertThatCode(() -> orderService.getOrdersPage(request)).doesNotThrowAnyException();

        verify(orderRepository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Получение заказа по id")
    void findOrderById() {
        var order = buildDefaultOrder();

        when(orderRepository.findOrderById(any(UUID.class))).thenReturn(Optional.ofNullable(order));

        var result = orderService.findOrderById(UUID.randomUUID());

        assertNotNull(result);
        verify(orderRepository).findOrderById(any(UUID.class));
    }

    @Test
    @DisplayName("Ошибка плучения заказа")
    void findOrderById_exception() {
        when(orderRepository.findOrderById(any(UUID.class))).thenThrow(OrderNotFoundException.class);

        assertThatCode(() -> orderService.findOrderById(UUID.randomUUID()))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    @DisplayName("Удаление заказа")
    void deleteOrderById() {
        var order = buildDefaultOrder();

        when(orderRepository.findOrderById(any())).thenReturn(Optional.ofNullable(order));

        var captor = ArgumentCaptor.forClass(OrderEntity.class);

        assertThatCode(() -> orderService.deleteOrderById(UUID.randomUUID())).doesNotThrowAnyException();

        verify(orderRepository).save(captor.capture());
        assertTrue(captor.getValue().isDeleted());
    }
}
