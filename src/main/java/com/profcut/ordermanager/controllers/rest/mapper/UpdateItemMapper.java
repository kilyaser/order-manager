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
    default void afterMapping(OrderItemFieldsPatch source, @MappingTarget OrderItemEntity target) {
        validateVatInclude(source, target);
        target.calculateTotalPrice();
    }

    private void validateVatInclude(OrderItemFieldsPatch source, OrderItemEntity target) {
        ofNullable(source.getIsVatInclude())
                .ifPresent(vatInclude -> {
                    boolean orderVatInclude = target.getOrder().isVatInclude();
                    if (vatInclude != orderVatInclude) {
                        throw OrderItemVatMissMatchException.byVatInclude(vatInclude, orderVatInclude, target.getOrder().getOrderId());
                    }
                    target.setVatInclude(vatInclude);
                    target.calculateVat();
                });
    }
}
