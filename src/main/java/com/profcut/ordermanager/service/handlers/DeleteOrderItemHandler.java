package com.profcut.ordermanager.service.handlers;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderMapper;
import com.profcut.ordermanager.domain.dto.order.DeleteOrderItemRequest;
import com.profcut.ordermanager.domain.dto.order.UiOrder;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.service.OrderService;
import jakarta.persistence.EntityManager;
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
    private final EntityManager entityManager;

    @Transactional
    public UiOrder handle(DeleteOrderItemRequest request) {
        log.info("invoke DeleteOrderItemHandler#handle by request: {}", request);
        var order = orderService.findOrderById(request.getOrderId());
        orderItemService.deleteOrderItems(request.getDeleteItemIds());
        entityManager.refresh(order);
        order.recalculateCurrentSum();
        return uiOrderMapper.apply(orderService.saveOrder(order));
    }
}
