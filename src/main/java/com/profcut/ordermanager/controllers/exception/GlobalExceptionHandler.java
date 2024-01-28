package com.profcut.ordermanager.controllers.exception;

import com.profcut.ordermanager.domain.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorHttpResponseFactory errorHttpResponseFactory;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error("GlobalExceptionHandler catch EntityNotFoundException", exception);
        var error = errorHttpResponseFactory.make(exception, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(error.getCode()).body(error);
    }
}
