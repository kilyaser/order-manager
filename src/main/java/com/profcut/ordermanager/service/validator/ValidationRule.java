package com.profcut.ordermanager.service.validator;

public interface ValidationRule<T> {

     ValidationResult validate(T value);
}
