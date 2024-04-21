package com.profcut.ordermanager.massaging.events;

import com.profcut.ordermanager.domain.entities.PaymentEntity;
import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class ChangePaymentEvent {

    UUID orderId;
    PaymentEntity payment;
    boolean isDelete;
}
