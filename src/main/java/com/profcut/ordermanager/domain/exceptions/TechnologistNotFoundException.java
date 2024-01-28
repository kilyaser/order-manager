package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class TechnologistNotFoundException extends EntityNotFoundException {

    public TechnologistNotFoundException(String message) {
        super(message);
    }

    public static TechnologistNotFoundException byTechnologistName(String fullName) {
        return new TechnologistNotFoundException("Не найден технолог с Ф.И.О.: " + fullName);
    }

    public static TechnologistNotFoundException byTechnologistId(UUID id) {
        return new TechnologistNotFoundException("Не найден технолог по id: " + id);
    }
}
