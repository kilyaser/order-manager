package com.profcut.ordermanager.domain.exceptions;

public class OmUserNotFoundException extends EntityNotFoundException {

    public OmUserNotFoundException(String message) {
        super(message);
    }

    public static OmUserNotFoundException byEmail(String email) {
        return new OmUserNotFoundException("Не найден пользователь по email: %s".formatted(email));
    }
}
