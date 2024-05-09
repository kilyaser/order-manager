package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class CncMachineNotFoundException extends EntityNotFoundException {

    public CncMachineNotFoundException(String message) {
        super(message);
    }

    public static CncMachineNotFoundException byMachineId(UUID machineId) {
        return new CncMachineNotFoundException("Станок с id: %s не найден.".formatted(machineId));
    }
}
