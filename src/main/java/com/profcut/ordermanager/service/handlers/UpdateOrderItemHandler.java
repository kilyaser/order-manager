package com.profcut.ordermanager.service.handlers;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderItemMapper;
import com.profcut.ordermanager.domain.dto.order.UiOrderItems;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderItemRequest;
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
public class UpdateOrderItemHandler {
// TODO: продумать реализацию обновления позиций заказа без ипользования entityManager
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final UiOrderItemMapper uiOrderItemMapper;
    private final EntityManager entityManager;

    @Transactional
    public UiOrderItems handle(UpdateOrderItemRequest request) {
        log.info("invoke UpdateOrderItemHandler#handle by request: {}", request);
        var order = orderService.findOrderById(request.getOrderId());
        request.getPatch().forEach(orderItemService::updateOrderItem);
        entityManager.refresh(order);
        order.recalculateCurrentSum();
        var uiItems = order.getOrderItems().stream()
                .map(uiOrderItemMapper)
                .toList();
        return new UiOrderItems(order.getOrderId(), uiItems);
    }
}
