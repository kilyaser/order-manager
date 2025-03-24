package com.profcut.ordermanager.controllers.rest.handlers;

import com.profcut.ordermanager.domain.dto.order.UiOrderAvailableStateAction;
import com.profcut.ordermanager.service.impl.OrderConstraintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetAvailableOrderStateHandler {

    private final OrderConstraintService constraintService;

    @Transactional(readOnly = true)
    public UiOrderAvailableStateAction handle(UUID orderId) {
        var availableAction = constraintService.getAvailableOrderStates(orderId);
        return new UiOrderAvailableStateAction(availableAction);
    }
}
