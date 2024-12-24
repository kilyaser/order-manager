package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.order.UiOrder;
import com.profcut.ordermanager.domain.dto.payment.UiPaymentShort;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Comparator;
import java.util.function.Function;

@Mapper(uses = {
        UiCounterpartyShortMapper.class,
        UiOrderItemMapper.class,
        UiPaymentShortMapper.class
})
public interface UiOrderMapper extends Function<OrderEntity, UiOrder> {

    @Override
    UiOrder apply(OrderEntity order);

    @AfterMapping
    default void sortPayments(@MappingTarget UiOrder order) {
        order.getPayments().sort(
                Comparator.comparing(UiPaymentShort::getPaymentDate,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                        .reversed());
    }
}
