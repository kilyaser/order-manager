package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.CncMachineCreateMapper;
import com.profcut.ordermanager.domain.dto.machine.CreateMachineRequest;
import com.profcut.ordermanager.domain.dto.machine.UpdateMachineRequest;
import com.profcut.ordermanager.domain.entities.CncMachineEntity;
import com.profcut.ordermanager.domain.exceptions.CncMachineNotFoundException;
import com.profcut.ordermanager.domain.repository.CncMachineRepository;
import com.profcut.ordermanager.service.CncMachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class CncMachineServiceImpl implements CncMachineService {

    private final CncMachineRepository machineRepository;
    private final CncMachineCreateMapper cncMachineCreateMapper;

    @Override
    @Transactional(readOnly = true)
    public CncMachineEntity findById(UUID machineId) {
        return machineRepository.findById(machineId).orElseThrow(() -> CncMachineNotFoundException.byMachineId(machineId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CncMachineEntity> findAll() {
        return machineRepository.findAll();
    }

    @Override
    public List<CncMachineEntity> findOccupiedByOrderItemId(UUID orderItemId) {
        return machineRepository.findCncMachineEntitiesByOrderItemId(orderItemId);
    }

    @Override
    public List<CncMachineEntity> saveAll(List<CncMachineEntity> machines) {
        return machineRepository.saveAll(machines);
    }

    @Override
    @Transactional
    public CncMachineEntity createMachine(CreateMachineRequest createRequest) {
        log.info("invoke 'CncMachineServiceImpl#createMachine' with createRequest={}", createRequest);
        var machine = cncMachineCreateMapper.apply(createRequest);
        return machineRepository.save(machine);
    }

    //TODO: убрать реализацию в handler
    @Override
    @Transactional
    public CncMachineEntity updateMachine(UpdateMachineRequest updateRequest) {
        log.info("invoke 'CncMachineServiceImpl#updateMachine' with updateRequest={}", updateRequest);
        var machine = findById(updateRequest.getMachineId());
        ofNullable(updateRequest.getMachineType()).ifPresent(machine::setMachineType);
        ofNullable(updateRequest.getName()).ifPresent(machine::setName);
        return machineRepository.save(machine);
    }

    @Override
    @Transactional
    public void deleteMachineById(UUID machineId) {
        log.info("invoke 'CncMachineServiceImpl#deleteMachineById' with id:{}", machineId);
        machineRepository.deleteById(machineId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CncMachineEntity> findAllByMachineIds(Set<UUID> machineIds) {
        return machineRepository.findAllByMachineIds(machineIds);
    }
}
