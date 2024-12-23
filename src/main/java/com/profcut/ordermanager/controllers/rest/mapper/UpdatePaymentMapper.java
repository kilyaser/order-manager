package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.payment.PaymentFieldsPatch;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdatePaymentMapper {

    @Mapping(target = "paymentDate", ignore = true)
    void updatePayment(PaymentFieldsPatch source, @MappingTarget PaymentEntity target);

    @AfterMapping
    default void afterMapping(PaymentFieldsPatch source, @MappingTarget PaymentEntity target) {
        target.setPaymentDate(source.getPaymentDate().atStartOfDay());
    }
}
