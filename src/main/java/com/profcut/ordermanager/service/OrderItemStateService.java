package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.stateprocessor.itemprocessor.ItemStateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemStateService {

    private final List<ItemStateProcessor> stateProcessors;

    @Transactional
    public void execute(OrderItemEntity item){
        stateProcessors.stream()
                .filter(processor -> processor.supportedState(item.getPreparationState()))
                .findFirst()
                .ifPresent(processor -> processor.changeState(item));
    }
}
