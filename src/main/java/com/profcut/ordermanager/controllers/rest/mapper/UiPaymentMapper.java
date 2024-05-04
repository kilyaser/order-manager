package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.payment.UiPayment;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper(uses = {
        UiCounterpartyShortMapper.class,
        UiOrderShortMapper.class})
public interface UiPaymentMapper extends Function<PaymentEntity, UiPayment> {

    @Override
    UiPayment apply(PaymentEntity payment);
}
