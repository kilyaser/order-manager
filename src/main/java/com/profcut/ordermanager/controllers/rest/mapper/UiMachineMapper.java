package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.machine.UiMachine;
import com.profcut.ordermanager.domain.entities.CncMachineEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper(uses = {
        UiOrderShortMapper.class,
        UiOrderItemMapper.class,
})
public interface UiMachineMapper extends Function<CncMachineEntity, UiMachine> {

    @Override
    UiMachine apply(CncMachineEntity cncMachineEntity);
}
