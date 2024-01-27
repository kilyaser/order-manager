package com.profcut.ordermanager.domain.enums;

import lombok.Getter;

@Getter
public enum ProductType {

    NEW("Новое изделие"),
    REPEAT("Повторное изделие"),
    CHANGE("Изделие с изменениями"),
    NOT_MAKE("Не изготовляем"),
    COOPERATION("Кооперативное иготовление");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }
}
