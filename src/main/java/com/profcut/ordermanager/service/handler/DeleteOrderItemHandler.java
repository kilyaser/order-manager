package com.profcut.ordermanager.service.handler;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderMapper;
import com.profcut.ordermanager.domain.dto.order.DeleteOrderItemRequest;
import com.profcut.ordermanager.domain.dto.order.UiOrder;
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

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final UiOrderMapper uiOrderMapper;

    @Transactional
    public UiOrder handle(DeleteOrderItemRequest request) {
        log.info("invoke DeleteOrderItemHandler#handle by request: {}", request);
        var order = orderService.findOrderById(request.getOrderId());
        order.getOrderItems().removeIf(i -> request.getDeleteItemIds().contains(i.getId()));
        orderItemService.deleteOrderItems(request.getDeleteItemIds());
        order.recalculateCurrentSum();
        return uiOrderMapper.apply(orderService.saveOrder(order));
    }
}
