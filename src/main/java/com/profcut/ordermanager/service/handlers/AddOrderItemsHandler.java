package com.profcut.ordermanager.service.handlers;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderItemMapper;
import com.profcut.ordermanager.domain.dto.order.AddOrderItemsRequest;
import com.profcut.ordermanager.domain.dto.order.UiOrderItems;
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
    private final UiOrderItemMapper uiOrderItemMapper;

    @Transactional
    public UiOrderItems handle(AddOrderItemsRequest requests) {
        log.info("invoke AddOrderItemHandler#handle with request: {}", requests);
        var order = orderService.findOrderById(requests.getOrderId());
        requests.getItemsRequest().forEach(iRequest -> iRequest.setVatInclude(order.isVatInclude()));
        var newItems = orderItemService.createOrderItems(requests.getItemsRequest());
        order.addItems(newItems);
        orderItemService.saveAll(newItems);
        orderService.saveOrder(order);
        var uiItems = order.getOrderItems().stream()
                .map(uiOrderItemMapper)
                .toList();
        return new UiOrderItems(order.getOrderId(), uiItems);
    }
}
