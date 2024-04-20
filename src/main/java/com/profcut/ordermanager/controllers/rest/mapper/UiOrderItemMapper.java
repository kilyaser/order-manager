package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.order.UiOrderItem;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper(uses = {
        UiProductMapper.class,
        UiMaterialMapper.class,
        UiTechnologistMapper.class
})
public interface UiOrderItemMapper extends Function<OrderItemEntity, UiOrderItem> {

    @Override
    UiOrderItem apply(OrderItemEntity orderItemEntity);
}
