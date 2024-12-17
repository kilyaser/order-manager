package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.order.UiOrderShort;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.function.Function;

@Mapper
public interface UiOrderShortMapper extends Function<OrderEntity, UiOrderShort> {

    @Override
    @Mapping(target = "counterpartyName", source = "order.counterparty.name")
    UiOrderShort apply(OrderEntity order);
}
