package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.UiContractCreateMapper;
import com.profcut.ordermanager.domain.dto.contract.CreateContractRequest;
import com.profcut.ordermanager.domain.entities.ContractEntity;
import com.profcut.ordermanager.domain.exceptions.ContractNotFoundException;
import com.profcut.ordermanager.domain.repository.ContractRepository;
import com.profcut.ordermanager.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final UiContractCreateMapper uiContractCreateMapper;

    @Override
    public List<ContractEntity> findByAllCounterpartyId(UUID counterpartyId) {
        return contractRepository.findByCounterpartyId(counterpartyId);
    }

    @Override
    public ContractEntity createContract(CreateContractRequest createRequest) {
        log.info("Create contract request: {}", createRequest);
        return contractRepository.save(uiContractCreateMapper.apply(createRequest));
    }

    @Override
    public ContractEntity findById(UUID contractId) {
        return contractRepository.findById(contractId).orElseThrow(() -> ContractNotFoundException.byId(contractId));
    }
}
