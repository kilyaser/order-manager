package com.profcut.ordermanager.domain.enums;

import lombok.Getter;

@Getter
public enum ContractState {

    ACTIVE("Активен"),
    COMPLETED("Завершен"),
    SUSPENDED("Приостановлен"),
    TERMINATED("Расторгнут");

    private final String description;

    ContractState(String description) {
        this.description = description;
    }
}
