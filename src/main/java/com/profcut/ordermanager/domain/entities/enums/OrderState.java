package com.profcut.ordermanager.domain.entities.enums;

import lombok.Getter;

@Getter
public enum OrderState {

    NEW("Новый"),
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
