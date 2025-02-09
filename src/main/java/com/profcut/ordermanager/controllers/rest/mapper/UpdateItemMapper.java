package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.order.OrderItemFieldsPatch;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.exceptions.OrderItemVatMissMatchException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static java.util.Optional.ofNullable;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateItemMapper {

    void updateOrderItem(OrderItemFieldsPatch source, @MappingTarget OrderItemEntity target);

    @AfterMapping
    default void afterMapping(@MappingTarget OrderItemEntity target) {
        target.calculateTotalPrice();
    }
}
