package com.profcut.ordermanager.service.validator;

import java.util.List;

public abstract class BaseValidator<T> {

    protected T validate(T value, List<? extends ValidationRule<T>> rules) {
        rules.stream()
                .map(rule -> rule.validate(value))
                .filter(ValidationResult::isInvalid)
                .findFirst()
                .map(ValidationResult::validationError)
                .ifPresent(error -> {
                    throw error;
                });
        return value;
    }
}
