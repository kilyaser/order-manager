package com.profcut.ordermanager.service.handler;

import com.profcut.ordermanager.controllers.rest.mapper.UiMaterialMapper;
import com.profcut.ordermanager.controllers.rest.mapper.UiOrderMapper;
import com.profcut.ordermanager.domain.dto.order.AddOrderItemsRequest;
import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
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

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.buildDefaultOrder;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultOrderItem;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddOrderItemsHandlerTest {

    @Mock
    OrderService orderService;
    @Mock
    OrderItemService orderItemService;
    @Mock
    UiOrderMapper uiOrderMapper;
    @InjectMocks
    AddOrderItemsHandler addOrderItemsHandler;

    @Test
    @DisplayName("Добавление позиции в заказ")
    void handle() {
        var addItemReq = new AddOrderItemsRequest()
                .setOrderId(UUID.randomUUID())
                .setItemsRequest(List.of(
                        OrderItemRequest.builder()
                                .productId(UUID.randomUUID())
                                .quantity(10)
                                .build()
                ));
        var item = getDefaultOrderItem().setId(UUID.randomUUID());
        var order = buildDefaultOrder();

        when(orderService.findOrderById(any())).thenReturn(order);
        when(orderItemService.createOrderItems(any())).thenReturn(List.of(item));

        assertThatCode(() -> addOrderItemsHandler.handle(addItemReq)).doesNotThrowAnyException();

        verify(orderService).findOrderById(any(UUID.class));
        verify(orderItemService).createOrderItems(any());
        verify(uiOrderMapper).apply(any());
        verify(orderService).saveOrder(any(OrderEntity.class));
    }
}
