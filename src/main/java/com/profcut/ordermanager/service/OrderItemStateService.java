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
    public boolean execute(OrderItemEntity item){
        return stateProcessors.stream()
                .filter(processor -> processor.supportedState(item.getPreparationState()))
                .findFirst()
                .map(processor -> processor.changeState(item))
                .orElse(false);
    }
}
