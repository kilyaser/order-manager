package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.counterparty.CreateCounterpartyRequest;
import com.profcut.ordermanager.domain.dto.counterparty.UpdateCounterpartyRequest;
import com.profcut.ordermanager.domain.dto.filter.FilterRequest;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.filter.SearchRequest;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CounterpartyService {

    CounterpartyEntity findById(UUID id);

    Page<CounterpartyEntity> findCounterpartiesByFilter(FilterRequest filter);

    List<CounterpartyEntity> getCounterparties(SearchRequest searchRequest);

    Page<CounterpartyEntity> getCounterpartiesPage(PageRequest request);

    CounterpartyEntity createCounterparty(CreateCounterpartyRequest request);

    CounterpartyEntity updateCounterparty(UpdateCounterpartyRequest request);

    void deleteCounterpartyById(UUID counterpartyId);
}
