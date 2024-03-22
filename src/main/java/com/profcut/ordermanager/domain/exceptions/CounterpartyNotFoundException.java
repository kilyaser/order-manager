package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class CounterpartyNotFoundException extends EntityNotFoundException {

    public CounterpartyNotFoundException(String message) {
        super(message);
    }

    public static CounterpartyNotFoundException byCounterpartyId(UUID id) {
        return new CounterpartyNotFoundException("Не найден контрагент по id: %s".formatted(id));
    }
}
