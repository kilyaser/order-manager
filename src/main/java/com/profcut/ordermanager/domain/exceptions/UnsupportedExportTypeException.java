package com.profcut.ordermanager.domain.exceptions;

import org.springframework.util.MimeType;

public class UnsupportedExportTypeException extends RuntimeException {
    public UnsupportedExportTypeException(MimeType exportType) {
        super("Тип документа [%s] не поддерживается".formatted(exportType));
    }
}
