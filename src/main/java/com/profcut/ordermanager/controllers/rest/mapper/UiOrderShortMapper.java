package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.order.UiOrderShort;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface UiOrderShortMapper extends Function<OrderEntity, UiOrderShort> {

    @Override
    UiOrderShort apply(OrderEntity order);
}