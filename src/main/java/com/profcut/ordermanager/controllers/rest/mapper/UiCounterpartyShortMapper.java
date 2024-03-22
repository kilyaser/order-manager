package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.counterparty.UiCounterpartyShort;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface UiCounterpartyShortMapper extends Function<CounterpartyEntity, UiCounterpartyShort> {

    @Override
    UiCounterpartyShort apply(CounterpartyEntity counterpartyEntity);
}
