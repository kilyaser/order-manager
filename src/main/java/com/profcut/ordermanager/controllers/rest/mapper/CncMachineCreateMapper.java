package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.machine.CreateMachineRequest;
import com.profcut.ordermanager.domain.entities.CncMachineEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface CncMachineCreateMapper extends Function<CreateMachineRequest, CncMachineEntity> {

    @Override
    CncMachineEntity apply(CreateMachineRequest createMachineRequest);
}
