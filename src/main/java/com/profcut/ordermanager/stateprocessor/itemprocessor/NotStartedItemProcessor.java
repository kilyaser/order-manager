package com.profcut.ordermanager.stateprocessor.itemprocessor;

import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.enums.PreparationState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.profcut.ordermanager.domain.enums.PreparationState.IN_PROCESS;
import static com.profcut.ordermanager.domain.enums.PreparationState.NOT_STARTED;

@Component
@RequiredArgsConstructor
public class NotStartedItemProcessor implements ItemStateProcessor {


    @Override
    @Transactional
    public void changeState(OrderItemEntity item) {
        if(!item.getMachines().isEmpty()) {
            item.setPreparationState(IN_PROCESS);
        }
    }

    @Override
    public boolean supportedState(PreparationState state) {
        return NOT_STARTED == state;
    }
}
