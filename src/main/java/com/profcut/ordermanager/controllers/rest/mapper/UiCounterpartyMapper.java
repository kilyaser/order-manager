package com.profcut.ordermanager.controllers.rest.mapper;


import com.profcut.ordermanager.domain.dto.counterparty.UiCounterparty;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper(uses = {
        UiContractMapper.class
})
public interface UiCounterpartyMapper extends Function<CounterpartyEntity, UiCounterparty> {

    @Override
    UiCounterparty apply(CounterpartyEntity counterpartyEntity);
}
