package com.profcut.ordermanager.service.handlers;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderMapper;
import com.profcut.ordermanager.domain.dto.order.DeleteOrderItemRequest;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteOrderItemHandlerTest {

    @Mock
    OrderItemService orderItemService;
    @Mock
    OrderService orderService;
    @Mock
    EntityManager entityManager;
    @Mock
    UiOrderMapper mapper;

    @InjectMocks
    DeleteOrderItemHandler handler;

    @Test
    @DisplayName("Удаление позиции заказа")
    void deleteOrderItem() {
        var deleteRequest = new DeleteOrderItemRequest()
                .setOrderId(UUID.randomUUID())
                .setDeleteItemIds(Set.of(UUID.randomUUID()));
        var order = TestDataHelper.buildDefaultOrder();

        when(orderService.findOrderById(deleteRequest.getOrderId())).thenReturn(order);
        when(orderService.saveOrder(any())).thenReturn(order);

        assertThatCode(() -> handler.handle(deleteRequest)).doesNotThrowAnyException();

        verify(orderService).findOrderById(any(UUID.class));
        verify(entityManager).refresh(any(OrderEntity.class));
        verify(orderItemService).deleteOrderItems(any());
        verify(orderService).saveOrder(any(OrderEntity.class));
        verify(mapper).apply(any(OrderEntity.class));
    }
}
