package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class OmUserNotFoundException extends EntityNotFoundException {

    public OmUserNotFoundException(String message) {
        super(message);
    }

    public static OmUserNotFoundException byEmail(String email) {
        return new OmUserNotFoundException("Не найден пользователь по email: %s".formatted(email));
    }

    public static OmUserNotFoundException byId(UUID id) {
        return new OmUserNotFoundException("Не найден пользователь по id: %s".formatted(id));
    }
}
