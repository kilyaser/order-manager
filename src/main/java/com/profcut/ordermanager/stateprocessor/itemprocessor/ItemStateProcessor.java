package com.profcut.ordermanager.stateprocessor.itemprocessor;

import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.enums.PreparationState;

public interface ItemStateProcessor {

    boolean changeState(OrderItemEntity item);

    boolean supportedState(PreparationState state);
}
