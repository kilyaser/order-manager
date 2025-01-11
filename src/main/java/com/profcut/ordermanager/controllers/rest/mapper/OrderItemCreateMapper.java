package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.function.Function;

@Mapper
public interface OrderItemCreateMapper extends Function<OrderItemRequest, OrderItemEntity> {

    @Override
    @Mapping(target = "quantityShipped", defaultValue = "0")
    @Mapping(target = "preparationState", defaultValue = "NOT_STARTED")
    OrderItemEntity apply(OrderItemRequest orderItemRequest);
}
