package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class PaymentNotFoundException extends EntityNotFoundException{
    public PaymentNotFoundException(String message) {
        super(message);
    }

    public static PaymentNotFoundException byPaymentId(UUID paymentId) {
        return new PaymentNotFoundException("Платеж по id: %s не нейден".formatted(paymentId));
    }
}
