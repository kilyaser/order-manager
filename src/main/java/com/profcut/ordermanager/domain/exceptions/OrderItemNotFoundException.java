package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class OrderItemNotFoundException extends EntityNotFoundException {

    public OrderItemNotFoundException(String message) {
        super(message);
    }

    public static OrderItemNotFoundException byItemId(UUID id) {
        return new OrderItemNotFoundException("Позиция заказа по id: %s не найден".formatted(id));
    }
}
