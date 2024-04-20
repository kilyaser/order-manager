package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class OrderNotFoundException extends EntityNotFoundException {

    protected OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException byOrderId(UUID orderId) {
        return new OrderNotFoundException("Не найден заказ по id: %s".formatted(orderId));
    }
}
