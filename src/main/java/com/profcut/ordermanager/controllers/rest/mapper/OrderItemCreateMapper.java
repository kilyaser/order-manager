package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface OrderItemCreateMapper extends Function<OrderItemRequest, OrderItemEntity> {

    @Override
    OrderItemEntity apply(OrderItemRequest orderItemRequest);
}
