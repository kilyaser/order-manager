package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.stateprocessor.orderprocessor.OrderStateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStateService {

    private final List<OrderStateProcessor> stateProcessors;

    @Transactional
    public void execute(OrderEntity order) {
        stateProcessors.stream()
                .filter(processor -> processor.support(order.getOrderState()))
                .findFirst()
                .ifPresent(processor -> processor.changeState(order));
    }
}
