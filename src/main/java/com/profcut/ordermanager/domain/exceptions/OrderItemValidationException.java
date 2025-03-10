package com.profcut.ordermanager.domain.exceptions;

public class OrderItemValidationException extends RuntimeException {

    public OrderItemValidationException(String message) {
        super(message);
    }
}
