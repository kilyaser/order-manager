package com.profcut.ordermanager.domain.entities.enums;

import lombok.Getter;

@Getter
public enum MasterStatus {

    CREATED("Создан"),
    PROCESSING("Выполняется"),
    COMPLETED("Завершен");

    private final String description;

    MasterStatus(String description) {
        this.description = description;
    }
}
