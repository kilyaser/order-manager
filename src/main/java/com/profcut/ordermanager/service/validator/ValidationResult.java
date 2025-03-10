package com.profcut.ordermanager.service.validator;

public record ValidationResult(RuntimeException validationError) {

    boolean isInvalid() {
        return validationError != null;
    }

    public static ValidationResult valid() {
        return new ValidationResult(null);
    }

    public static ValidationResult invalid(RuntimeException exception) {
        return new ValidationResult(exception);
    }
}
