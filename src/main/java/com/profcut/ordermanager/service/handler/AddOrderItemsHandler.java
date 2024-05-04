package com.profcut.ordermanager.service.handler;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderMapper;
import com.profcut.ordermanager.domain.dto.order.AddOrderItemsRequest;
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
public class AddOrderItemsHandler {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final UiOrderMapper uiOrderMapper;

    @Transactional
    public UiOrder handle(AddOrderItemsRequest requests) {
        log.info("invoke AddOrderItemHandler#handle with request: {}", requests);
        var order = orderService.findOrderById(requests.getOrderId());
        order.getOrderItems().addAll(orderItemService.createOrderItems(order, requests.getItemsRequest()));
        order.recalculateCurrentSum();
        return uiOrderMapper.apply(orderService.saveOrder(order));
    }
}