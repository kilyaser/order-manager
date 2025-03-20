package com.profcut.ordermanager.controllers.rest.handlers;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderItemMapper;
import com.profcut.ordermanager.domain.dto.material.UiEstablishMachineRequest;
import com.profcut.ordermanager.domain.dto.order.UiOrderItem;
import com.profcut.ordermanager.service.CncMachineService;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.service.validator.EstablishMachineValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class EstablishMachineHandler {

    private final CncMachineService machineService;
    private final OrderItemService orderItemService;
    private final UiOrderItemMapper uiOrderItemMapper;
    private final EstablishMachineValidator validator;

    @Transactional
    public UiOrderItem handle(UiEstablishMachineRequest request) {
        log.info("Назначение станка request={}", request);
        validator.validate(request);
        var item = orderItemService.findById(request.getOrderItemId());
        item.clearMachine();
        if (CollectionUtils.isEmpty(request.getMachineIds())) {
            return uiOrderItemMapper.apply(item);
        }
        var machines = machineService.findAllByMachineIds(request.getMachineIds());
        item.addMachine(machines);
        orderItemService.saveItem(item);
        return uiOrderItemMapper.apply(item);
    }
}
