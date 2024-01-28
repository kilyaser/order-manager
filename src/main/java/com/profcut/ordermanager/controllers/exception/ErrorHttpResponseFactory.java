package com.profcut.ordermanager.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class ErrorHttpResponseFactory {

    public ErrorResponse make(Exception ex, HttpStatus status) {
        var causeClass = ofNullable(ex.getCause()).map(Object::getClass)
                .map(Class::getSimpleName)
                .orElse(null);
        var causeMessage = ofNullable(ex.getCause()).map(Throwable::getMessage).orElse(null);
        var response = ErrorResponse.builder()
                .exClass(ex.getClass().getSimpleName())
                .causeClass(causeClass)
                .code(status.value()).build();
        response.setMessage(ex.getMessage());
        response.setCauseMessage(causeMessage);
        return response;
    }
}
