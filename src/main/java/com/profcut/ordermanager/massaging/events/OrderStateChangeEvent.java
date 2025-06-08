package com.profcut.ordermanager.massaging.events;

import com.profcut.ordermanager.domain.enums.OrderState;

import java.util.UUID;

public record OrderStateChangeEvent(
        UUID orderId,
        OrderState prevState,
        OrderState newState
) {
}
