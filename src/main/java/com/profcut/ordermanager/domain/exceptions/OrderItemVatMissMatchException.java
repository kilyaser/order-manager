package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class OrderItemVatMissMatchException extends VatException {

    public OrderItemVatMissMatchException(String message) {
        super(message);
    }

    public static OrderItemVatMissMatchException byVatInclude(boolean itemVat, boolean orderVat, UUID orderId) {
        return new OrderItemVatMissMatchException(
                "Значение включения НДС в позицию заказа %s не соответствует значению %s в заказе: %s"
                        .formatted(itemVat, orderVat, orderId));
    }
}
