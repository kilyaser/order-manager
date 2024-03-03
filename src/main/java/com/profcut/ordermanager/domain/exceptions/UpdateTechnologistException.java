package com.profcut.ordermanager.domain.exceptions;

import com.profcut.ordermanager.controllers.rest.dto.technologist.TechnologistFieldsPatch;

public class UpdateTechnologistException extends RuntimeException {

    public UpdateTechnologistException(String message) {
        super(message);
    }

    public UpdateTechnologistException(String message, Throwable cause) {
        super(message, cause);
    }

    public static UpdateTechnologistException byPatch(TechnologistFieldsPatch patch, Exception ex) {
        return new UpdateTechnologistException("Ошибка обновления технолога, patch: %s".formatted(patch), ex);
    }
}
