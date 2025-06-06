package com.profcut.ordermanager.controllers.exception;

import com.profcut.ordermanager.domain.exceptions.EntityNotFoundException;
import com.profcut.ordermanager.domain.exceptions.OrderItemValidationException;
import com.profcut.ordermanager.domain.exceptions.VatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("GlobalExceptionHandler catch MethodArgumentNotValidException", exception);
        var error = errorHttpResponseFactory.make(exception, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        log.error("GlobalExceptionHandler catch AccessDeniedException", exception);
        var error = errorHttpResponseFactory.make(exception, HttpStatus.FORBIDDEN);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(VatException.class)
    public ResponseEntity<ErrorResponse> handleVatException(VatException exception) {
        log.error("GlobalExceptionHandler catch VatException", exception);
        var error = errorHttpResponseFactory.make(exception, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(OrderItemValidationException.class)
    public ResponseEntity<ErrorResponse> handleOrderItemValidationException(OrderItemValidationException exception) {
        log.error("GlobalExceptionHandler catch VatException", exception);
        var error = errorHttpResponseFactory.make(exception, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(error.getCode()).body(error);
    }
}
