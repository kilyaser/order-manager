package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.order.OrderFieldsPatch;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static java.util.Optional.ofNullable;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateOrderByPatchMapper {

    @Mapping(target = "governmentOrder", source = "isGovernmentOrder")
    @Mapping(target = "vatInclude", source = "isVatInclude")
    void updateOrder(OrderFieldsPatch source, @MappingTarget OrderEntity target);

    @AfterMapping
    default void updateItemVat(@MappingTarget OrderEntity target, OrderFieldsPatch source) {
        ofNullable(source.getIsVatInclude()).ifPresent(isInclude -> target.getOrderItems().forEach(item -> {
            item.setVatInclude(isInclude);
            item.calculateTotalPrice();
        }));
        target.recalculateOrderSum();
    }
}
