package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.counterparty.CreateCounterpartyRequest;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface CounterpartyCreateMapper extends Function<CreateCounterpartyRequest, CounterpartyEntity> {

    @Override
    CounterpartyEntity apply(CreateCounterpartyRequest request);
}
