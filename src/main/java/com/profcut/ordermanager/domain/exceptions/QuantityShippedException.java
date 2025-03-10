package com.profcut.ordermanager.domain.exceptions;

public class QuantityShippedException extends OrderItemValidationException {

    public QuantityShippedException(Integer excess) {
        super("Колличество отгруженной позиции превышает коллчиество в заказе на %s".formatted(excess));
    }
}
