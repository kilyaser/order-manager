package com.profcut.ordermanager.stateprocessor.orderprocessor;

import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.enums.OrderState;

public interface OrderStateProcessor {

    void changeState(OrderEntity order);

    boolean support(OrderState state);
}
