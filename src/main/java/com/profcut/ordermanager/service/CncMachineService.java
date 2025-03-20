package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.machine.CreateMachineRequest;
import com.profcut.ordermanager.domain.dto.machine.UpdateMachineRequest;
import com.profcut.ordermanager.domain.entities.CncMachineEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CncMachineService {

    CncMachineEntity findById(UUID machineId);

    List<CncMachineEntity> findAll();

    List<CncMachineEntity> findOccupiedByOrderItemId(UUID orderItemId);

    List<CncMachineEntity> saveAll(List<CncMachineEntity> machines);

    CncMachineEntity createMachine(CreateMachineRequest createRequest);

    CncMachineEntity updateMachine(UpdateMachineRequest updateRequest);

    void deleteMachineById(UUID machineId);

    List<CncMachineEntity> findAllByMachineIds(Set<UUID> machineIds);
}
