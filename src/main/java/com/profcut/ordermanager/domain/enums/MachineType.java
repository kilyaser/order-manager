package com.profcut.ordermanager.domain.enums;

import lombok.Getter;

@Getter
public enum MachineType {

    THREE_AXIS("Трехосевой обрабатывающий фрезерный станок"),
    FIVE_AXIS("Пятиосевой обрабатывающий фрезерный станок");

    private final String description;

    MachineType(String description) {
        this.description = description;
    }
}
