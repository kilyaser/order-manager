package com.profcut.ordermanager.domain.entities.enums;

public enum MasterStatus {
    CREATED("Создан"),
    PROCESSING("Выполняется"),
    COMPLETED("Завершен");

    private String description;
    public String getDescription() {
        return this.description;
    }
    private MasterStatus(String description) {
        this.description = description;
    }
}
