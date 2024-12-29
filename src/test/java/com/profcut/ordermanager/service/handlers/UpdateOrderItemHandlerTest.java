package com.profcut.ordermanager.service.handlers;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderItemMapper;
import com.profcut.ordermanager.domain.dto.order.OrderItemFieldsPatch;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderItemRequest;
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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateOrderItemHandlerTest {

    @Mock
    OrderItemService orderItemService;
    @Mock
    OrderService orderService;
    @Mock
    EntityManager entityManager;
    @Mock
    UiOrderItemMapper mapper;
    @InjectMocks
    UpdateOrderItemHandler updateOrderItemHandler;

    @Test
    @DisplayName("Обновление позиции заказа")
    void updateOrderItem() {
        var request = new UpdateOrderItemRequest()
                .setOrderId(UUID.randomUUID())
                .setPatch(List.of(new OrderItemFieldsPatch()
                        .setItemId(UUID.randomUUID())
                        .setProductId(UUID.randomUUID())
                        .setQuantity(5)));
        var order = TestDataHelper.buildDefaultOrder();

        when(orderService.findOrderById(request.getOrderId())).thenReturn(order);

        assertThatCode(() -> updateOrderItemHandler.handle(request)).doesNotThrowAnyException();

        verify(orderService).findOrderById(any(UUID.class));
        verify(orderItemService).updateOrderItem(any(OrderItemFieldsPatch.class));
        verify(entityManager).refresh(any(OrderEntity.class));
        verify(mapper).apply(any());
    }
}
