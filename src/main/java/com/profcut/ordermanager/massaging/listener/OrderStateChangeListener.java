package com.profcut.ordermanager.massaging.listener;

import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.massaging.events.OrderStateChangeEvent;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Set;

import static com.profcut.ordermanager.domain.enums.OrderState.CANCELLED;
import static com.profcut.ordermanager.domain.enums.OrderState.COMPLETED;

@Component
@RequiredArgsConstructor
public class OrderStateChangeListener {

    private final OrderItemService orderItemService;
    private final OrderService orderService;

    @TransactionalEventListener(classes = OrderStateChangeEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void handleStateChange(OrderStateChangeEvent event) {
        if (Set.of(COMPLETED, CANCELLED).contains(event.newState())) {
            var order = orderService.findOrderById(event.orderId());
            var items = order.getOrderItems().stream()
                    .peek(OrderItemEntity::clearMachine)
                    .toList();
            orderItemService.saveAll(items);
        }
    }
}
