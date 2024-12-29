package com.profcut.ordermanager.service.handlers;

import com.profcut.ordermanager.domain.dto.order.DeleteOrderItemRequest;
import com.profcut.ordermanager.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteOrderItemHandler {

    private final OrderItemService orderItemService;

    @Transactional
    public void handle(DeleteOrderItemRequest request) {
        orderItemService.deleteOrderItems(request.getDeleteItemIds());
    }
}
