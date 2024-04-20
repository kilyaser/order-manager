package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.order.UiOrder;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper(uses = {
        UiCounterpartyShortMapper.class,
        UiOrderItemMapper.class,
        UiPaymentShortMapper.class
})
public interface UiOrderMapper extends Function<OrderEntity, UiOrder> {

    @Override
    UiOrder apply(OrderEntity order);
}
