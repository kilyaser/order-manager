package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public static ProductNotFoundException byProductId(UUID productId) {
        return new ProductNotFoundException("Не найден продук по id: %s".formatted(productId));
    }
}
