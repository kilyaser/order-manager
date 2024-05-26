package com.profcut.ordermanager.domain.enums;

import lombok.Getter;

@Getter
public enum OrderState {

    INITIATION_NOT_COMPLETED("Не инициирован"),
    IN_WORK("В работе"),
    READY("Готов"),
    SHIPPED("Отгружен заказчику"),
    COMPLETED("Завершен"),
    CANCELLED("Отменен");

    private final String description;

    OrderState(String description) {
        this.description = description;
    }
}
