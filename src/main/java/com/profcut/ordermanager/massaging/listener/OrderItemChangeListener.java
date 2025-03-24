package com.profcut.ordermanager.massaging.listener;

import com.profcut.ordermanager.massaging.events.OrderItemChangeEvent;
import com.profcut.ordermanager.service.OrderItemStateService;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.service.OrderStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderItemChangeListener {

    private final OrderItemStateService orderItemStateService;
    private final OrderStateService orderStateService;
    private final OrderService orderService;

    @TransactionalEventListener(value =  OrderItemChangeEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void changeState(OrderItemChangeEvent event) {
        var isChanged = orderItemStateService.execute(event.item());
        if (isChanged) {
            var order = event.item().getOrder();
            orderStateService.execute(order);
            orderService.saveOrder(order);
        }
    }
}
