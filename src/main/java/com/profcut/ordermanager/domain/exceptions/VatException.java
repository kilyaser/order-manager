package com.profcut.ordermanager.domain.exceptions;

public abstract class VatException extends RuntimeException {

    protected VatException(String message) {
        super(message);
    }
}
