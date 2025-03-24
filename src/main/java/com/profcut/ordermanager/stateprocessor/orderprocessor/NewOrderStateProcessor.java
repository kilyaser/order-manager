package com.profcut.ordermanager.stateprocessor.orderprocessor;

import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.enums.OrderState;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.profcut.ordermanager.domain.enums.OrderState.IN_WORK;
import static com.profcut.ordermanager.domain.enums.OrderState.NEW;
import static com.profcut.ordermanager.domain.enums.PreparationState.NOT_STARTED;

@Component
public class NewOrderStateProcessor implements OrderStateProcessor {

    @Override
    public void changeState(OrderEntity order) {
        if (shouldChange(order.getOrderItems())) {
            order.setOrderState(IN_WORK);
        }
    }

    @Override
    public boolean support(OrderState state) {
        return NEW == state;
    }

    private boolean shouldChange(Set<OrderItemEntity> items) {
        return items.stream()
                .anyMatch(item -> item.getPreparationState() != NOT_STARTED);
    }
}
