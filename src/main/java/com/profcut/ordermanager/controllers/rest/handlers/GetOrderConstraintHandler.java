package com.profcut.ordermanager.controllers.rest.handlers;

import com.profcut.ordermanager.domain.dto.order.UiOrderConstraint;
import com.profcut.ordermanager.service.impl.OrderConstraintService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetOrderConstraintHandler {

    private final OrderConstraintService orderConstraintService;

    @Transactional
    public UiOrderConstraint handle(UUID orderId) {
        var constraints = new UiOrderConstraint()
                .setCadDeleteOrder(orderConstraintService.cadDeleteOrder(orderId))
                .setCanChangeOrder(orderConstraintService.canChangeOrder(orderId));
        log.info("Order id: {} constraints {}", orderId, constraints);
        return constraints;
    }
}
