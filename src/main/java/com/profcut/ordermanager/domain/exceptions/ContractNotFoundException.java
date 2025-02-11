package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class ContractNotFoundException extends EntityNotFoundException {

    private ContractNotFoundException(String message) {
        super(message);
    }

    public static ContractNotFoundException byId(UUID id) {
        return new ContractNotFoundException("Договор с id: %s не найден.".formatted(id));
    }
}
