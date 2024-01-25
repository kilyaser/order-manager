package com.profcut.ordermanager.domain.entities.enums;

public enum OrderState {
    NEW("Новый"),
    IN_WORK("В работе"),
    READY("Готов"),
    SHIPPED("Отгружен заказчику"),
    COMPLETED("Завершен"),
    CANCELLED("Отменен");

    private String description;
    public String getDescription() {
        return this.description;
    }
    private OrderState(String description) {
        this.description = description;
    }

}
