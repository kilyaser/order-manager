package com.profcut.ordermanager.domain.enums;

import lombok.Getter;

@Getter
public enum Constants {

    PASSWORD_UPDATED("Password updated successfully");

    private final String value;

    Constants(String value) {
        this.value = value;
    }
}
