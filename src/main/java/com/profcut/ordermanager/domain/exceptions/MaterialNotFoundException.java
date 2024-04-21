package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class MaterialNotFoundException extends EntityNotFoundException {

    public MaterialNotFoundException(String message) {
        super(message);
    }

    public static MaterialNotFoundException byMaterialId(UUID materialId) {
        return new MaterialNotFoundException("Материал по id: %s не найден".formatted(materialId));
    }
}
