package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.contract.CreateContractRequest;
import com.profcut.ordermanager.domain.entities.ContractEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface UiContractCreateMapper extends Function<CreateContractRequest, ContractEntity> {
}
