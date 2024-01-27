package com.profcut.ordermanager.domain.enums;


import lombok.Getter;

@Getter
public enum PreparationState {

    NOT_STARTED("Не начат"),
    IN_PROCESS("В прогрессе"),
    DONE("Готов");

    private final String description;

    PreparationState(String description) {
        this.description = description;
    }
}
