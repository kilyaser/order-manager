package com.profcut.ordermanager.massaging.listeners;

import com.profcut.ordermanager.massaging.events.ChangePaymentEvent;
import com.profcut.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentChangeEventListener {

    private final OrderService orderService;

    @TransactionalEventListener(classes = ChangePaymentEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void changePaymentProcess(ChangePaymentEvent event) {
        var payment = event.getPayment();
        var order = orderService.findOrderById(event.getOrderId());
        if (event.isDelete()) {
            order.getPayments().removeIf(p -> p.getPaymentId().equals(payment.getPaymentId()));
        } else {
            payment.setOrder(order);
            var existingPayment = order.getPayments().stream()
                    .filter(p -> p.getPaymentId().equals(payment.getPaymentId()))
                    .findFirst();
            existingPayment.ifPresentOrElse(existing -> {
                existing.setPaymentSum(payment.getPaymentSum());
            }, () -> order.getPayments().add(payment));
        }
        order.calculateDebtSum();
        orderService.saveOrder(order);
    }
}
