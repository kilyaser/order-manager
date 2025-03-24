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
public class InWorkOrderStateProcessor implements OrderStateProcessor {

    @Override
    public void changeState(OrderEntity order) {
        if (shouldChange(order.getOrderItems())) {
            order.setOrderState(READY);
        }
    }

    @Override
    public boolean support(OrderState state) {
        return IN_WORK == state;
    }

    private boolean shouldChange(Set<OrderItemEntity> items) {
        return items.stream()
                .allMatch(item -> item.getPreparationState() == DONE);
    }
}
