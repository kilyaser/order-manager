package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class EstablishMachineException extends OrderItemValidationException {

    public EstablishMachineException(UUID orderItemId) {
        super("Нельзя назначить станок если позиция %s равна 0".formatted(orderItemId));
    }
}
