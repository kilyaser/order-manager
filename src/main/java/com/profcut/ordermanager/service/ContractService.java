package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.contract.CreateContractRequest;
import com.profcut.ordermanager.domain.entities.ContractEntity;

import java.util.List;
import java.util.UUID;

public interface ContractService {

    List<ContractEntity> findByAllCounterpartyId(UUID counterpartyId);

    ContractEntity createContract(CreateContractRequest createRequest);

    ContractEntity findById(UUID contractId);
}
