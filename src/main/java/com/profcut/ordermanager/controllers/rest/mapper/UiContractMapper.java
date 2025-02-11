package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.contract.UiContract;
import com.profcut.ordermanager.domain.entities.ContractEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper(uses = {
        UiCounterpartyShortMapper.class
})
public interface UiContractMapper extends Function<ContractEntity, UiContract> {
}
