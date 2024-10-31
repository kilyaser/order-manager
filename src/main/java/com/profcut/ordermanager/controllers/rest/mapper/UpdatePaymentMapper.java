package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.payment.PaymentFieldsPatch;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdatePaymentMapper {

    void updatePayment(PaymentFieldsPatch source, @MappingTarget PaymentEntity target);
}
