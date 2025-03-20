package com.profcut.ordermanager.massaging.listener;

import com.profcut.ordermanager.massaging.events.OrderItemChangeEvent;
import com.profcut.ordermanager.service.OrderItemStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderItemChangeListener {

    private final OrderItemStateService orderItemStateService;

    @TransactionalEventListener(value =  OrderItemChangeEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void changeState(OrderItemChangeEvent event) {
        orderItemStateService.execute(event.item());
    }
}
