package com.profcut.ordermanager.stateprocessor.itemprocessor;

import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.enums.PreparationState;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.profcut.ordermanager.domain.enums.PreparationState.DONE;
import static com.profcut.ordermanager.domain.enums.PreparationState.IN_PROCESS;

@Component
public class DoneItemProcessor implements ItemStateProcessor {

    @Override
    @Transactional
    public boolean changeState(OrderItemEntity item) {
        if (item.getQuantity() > item.getQuantityShipped()) {
            item.setPreparationState(IN_PROCESS);
            return true;
        }
        return false;
    }

    @Override
    public boolean supportedState(PreparationState state) {
        return DONE == state;
    }
}
