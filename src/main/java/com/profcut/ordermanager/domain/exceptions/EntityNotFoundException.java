package com.profcut.ordermanager.domain.exceptions;

public abstract class EntityNotFoundException extends RuntimeException {

    protected EntityNotFoundException(String message) {
        super(message);
    }
}
