package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.UpdateOrderByPatchMapper;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.order.OrderFieldsPatch;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderRequest;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.enums.MasterStatus;
import com.profcut.ordermanager.domain.enums.OrderState;
import com.profcut.ordermanager.domain.exceptions.OrderNotFoundException;
import com.profcut.ordermanager.domain.repository.OrderRepository;
import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import com.profcut.ordermanager.service.ContractService;
import com.profcut.ordermanager.service.CounterpartyService;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.buildDefaultOrder;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultCreateOrderRequest;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultOrderItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    CurrentUserSecurityService currentUserSecurityService;
    @Mock
    OrderItemService orderItemService;
    @Mock
    CounterpartyService counterpartyService;
    @Mock
    ContractService contractService;
    @Spy
    UpdateOrderByPatchMapper mapper = Mappers.getMapper(UpdateOrderByPatchMapper.class);
    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    @DisplayName("Успешное создание заказа")
    void createOrder() {
        var request = getDefaultCreateOrderRequest();
        var saveOrder = buildDefaultOrder();

        when(counterpartyService.findById(any()))
                .thenReturn(new CounterpartyEntity().setId(UUID.randomUUID()).setName("name"));
        when(currentUserSecurityService.getLogin()).thenReturn("user@mail.ru");
        when(orderRepository.save(any())).thenReturn(saveOrder);
        when(orderItemService.createOrderItems(eq(request.getItemRequests()))).thenReturn(List.of(getDefaultOrderItem()));

        assertThatCode(() -> orderService.createOrder(request)).doesNotThrowAnyException();

        var captor = ArgumentCaptor.forClass(OrderEntity.class);

        verify(counterpartyService).findById(any(UUID.class));
        verify(currentUserSecurityService).getLogin();
        verify(orderItemService).createOrderItems(any());
        verify(orderRepository).save(captor.capture());
        verify(orderRepository).saveAndFlush(any());

        assertThat(captor.getValue())
                .isInstanceOf(OrderEntity.class)
                .satisfies(order -> {
                    assertThat(order.getOrderState()).isEqualTo(OrderState.NEW);
                    assertThat(order.getMasterStatus()).isEqualTo(MasterStatus.CREATED);
                    assertThat(order.getOrderName()).isEqualTo(request.getOrderName());
                });
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Получение страницы заказа")
    void getOrdersPage() {
        var request = new PageRequest().setPage(0).setSize(20);

        assertThatCode(() -> orderService.getOrdersPage(request)).doesNotThrowAnyException();

        verify(orderRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Обновление Заказа")
    void updateOrder() {
        var order = TestDataHelper.buildDefaultOrder();
        var patch = new OrderFieldsPatch()
                .setOrderName("newOrderName")
                .setBillNumber("№2024-2")
                .setIsGovernmentOrder(true);
            var request = new UpdateOrderRequest()
                .setId(order.getOrderId())
                .setPatch(patch);
        var captor = ArgumentCaptor.forClass(OrderEntity.class);

        when(orderRepository.findOrderById(order.getOrderId())).thenReturn(Optional.of(order));

        assertThatCode(() -> orderService.updateOrder(request)).doesNotThrowAnyException();

        verify(orderRepository).findOrderById(order.getOrderId());
        verify(mapper).updateOrder(patch, order);
        verify(orderRepository).save(captor.capture());

        assertThat(captor.getValue())
                .isInstanceOf(OrderEntity.class)
                .satisfies(order1 -> {
                    assertThat(order.getOrderName()).isEqualTo(patch.getOrderName());
                    assertThat(order.getBillNumber()).isEqualTo(patch.getBillNumber());
                    assertThat(order.isGovernmentOrder()).isEqualTo(patch.getIsGovernmentOrder());
                });
    }

    @ParameterizedTest
    @EnumSource(value = OrderState.class,
            names = {"IN_WORK", "READY"})
    @DisplayName("Изменение статуса заказ")
    void changeState(OrderState state) {
        var order = TestDataHelper.buildDefaultOrder();
        var captor = ArgumentCaptor.forClass(OrderEntity.class);

        when(orderRepository.findOrderById(order.getOrderId())).thenReturn(Optional.of(order));

        assertThatCode(() -> orderService.changeState(order.getOrderId(), state)).doesNotThrowAnyException();

        verify(orderRepository).save(captor.capture());

        assertEquals(state, captor.getValue().getOrderState());
    }

    @Test
    @DisplayName("Получение заказа по id")
    void findOrderById() {
        var order = buildDefaultOrder();

        when(orderRepository.findOrderById(any(UUID.class))).thenReturn(Optional.of(order));

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

        when(orderRepository.findOrderById(any())).thenReturn(Optional.of(order));

        var captor = ArgumentCaptor.forClass(OrderEntity.class);

        assertThatCode(() -> orderService.deleteOrderById(UUID.randomUUID())).doesNotThrowAnyException();

        verify(orderRepository).save(captor.capture());
        assertTrue(captor.getValue().isDeleted());
    }
}
