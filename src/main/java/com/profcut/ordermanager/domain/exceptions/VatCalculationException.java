package com.profcut.ordermanager.domain.exceptions;

public class VatCalculationException extends RuntimeException {

    public VatCalculationException(String message) {
        super(message);
    }

    public static VatCalculationException byCalculateVat() {
        return new VatCalculationException("Ошибка расчета НДС, параметр totalPrice не обределен");
    }
}
