package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.payment.UiPaymentShort;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface UiPaymentShortMapper extends Function<PaymentEntity, UiPaymentShort> {

    @Override
    UiPaymentShort apply(PaymentEntity paymentEntity);
}
