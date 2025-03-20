package com.profcut.ordermanager.controllers.rest.handlers;

import com.profcut.ordermanager.domain.dto.order.DeleteOrderItemRequest;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteOrderItemHandler {

    private final OrderItemService orderItemService;
    private final OrderService orderService;

    @Transactional
    public void handle(DeleteOrderItemRequest request) {
        orderItemService.deleteOrderItems(request.getDeleteItemIds());
        var order = orderService.findOrderById(request.getOrderId());
        order.recalculateOrderSum();
        orderService.saveOrder(order);
    }
}
