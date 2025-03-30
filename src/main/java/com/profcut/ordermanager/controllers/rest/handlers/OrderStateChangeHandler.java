package com.profcut.ordermanager.controllers.rest.handlers;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderMapper;
import com.profcut.ordermanager.domain.dto.order.UiOrder;
import com.profcut.ordermanager.domain.enums.OrderState;
import com.profcut.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStateChangeHandler {

    private final OrderService orderService;
    private final UiOrderMapper uiOrderMapper;

    @Transactional
    public UiOrder handle(UUID orderId, OrderState toState) {
        return uiOrderMapper.apply(orderService.changeState(orderId, toState));
    }
}
