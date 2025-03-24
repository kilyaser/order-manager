package com.profcut.ordermanager.stateprocessor.orderprocessor;

import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.enums.OrderState;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.profcut.ordermanager.domain.enums.OrderState.IN_WORK;
import static com.profcut.ordermanager.domain.enums.OrderState.READY;
import static com.profcut.ordermanager.domain.enums.PreparationState.DONE;

@Component
public class ReadyOrderStateProcessor implements OrderStateProcessor {

    @Override
    public void changeState(OrderEntity order) {
        if (shouldChangeToInWork(order.getOrderItems())) {
            order.setOrderState(IN_WORK);
        }
    }

    @Override
    public boolean support(OrderState state) {
        return READY == state;
    }

    private boolean shouldChangeToInWork(Set<OrderItemEntity> items) {
        return items.stream()
                .anyMatch(item -> DONE != item.getPreparationState());
    }
}
