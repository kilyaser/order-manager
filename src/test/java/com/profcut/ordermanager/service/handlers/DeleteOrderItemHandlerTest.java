package com.profcut.ordermanager.service.handlers;

import com.profcut.ordermanager.domain.dto.order.DeleteOrderItemRequest;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
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

    @InjectMocks
    DeleteOrderItemHandler handler;

    @Test
    @DisplayName("Удаление позиции заказа")
    void deleteOrderItem() {
        var deleteRequest = new DeleteOrderItemRequest()
                .setOrderId(UUID.randomUUID())
                .setDeleteItemIds(Set.of(UUID.randomUUID()));

        when(orderService.findOrderById(any())).thenReturn(TestDataHelper.buildDefaultOrder());

        assertThatCode(() -> handler.handle(deleteRequest)).doesNotThrowAnyException();

        verify(orderItemService).deleteOrderItems(any());
        verify(orderService).findOrderById(deleteRequest.getOrderId());
        verify(orderService).saveOrder(any());
    }
}
