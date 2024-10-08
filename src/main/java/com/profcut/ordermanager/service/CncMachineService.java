package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.machine.CreateMachineRequest;
import com.profcut.ordermanager.domain.dto.machine.UpdateMachineRequest;
import com.profcut.ordermanager.domain.entities.CncMachineEntity;

import java.util.UUID;

public interface CncMachineService {

    CncMachineEntity findById(UUID machineId);

    CncMachineEntity createMachine(CreateMachineRequest createRequest);

    CncMachineEntity updateMachine(UpdateMachineRequest updateRequest);

    void deleteMachineById(UUID machineId);
}
