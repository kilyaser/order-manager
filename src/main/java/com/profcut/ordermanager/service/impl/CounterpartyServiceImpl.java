package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.CounterpartyCreateMapper;
import com.profcut.ordermanager.domain.dto.counterparty.CounterpartyFieldsPatch;
import com.profcut.ordermanager.domain.dto.counterparty.CreateCounterpartyRequest;
import com.profcut.ordermanager.domain.dto.counterparty.UpdateCounterpartyRequest;
import com.profcut.ordermanager.domain.dto.filter.FilterRequest;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import com.profcut.ordermanager.domain.exceptions.CounterpartyNotFoundException;
import com.profcut.ordermanager.domain.repository.CounterpartyRepository;
import com.profcut.ordermanager.service.CounterpartyService;
import com.profcut.ordermanager.utils.jpa.specification.CounterpartySpecification;
import com.profcut.ordermanager.utils.jpa.specification.PageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class CounterpartyServiceImpl implements CounterpartyService {

    private final CounterpartyRepository counterpartyRepository;
    private final CounterpartyCreateMapper counterpartyCreateMapper;

    @Override
    @Transactional(readOnly = true)
    public CounterpartyEntity findById(UUID id) {
        return counterpartyRepository.findCounterpartyById(id).orElseThrow(() -> CounterpartyNotFoundException.byCounterpartyId(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CounterpartyEntity> findCounterpartiesByFilter(FilterRequest filter) {
        log.info("invoke CounterpartyServiceImpl#findCounterpartiesByFilter with filter={}", filter);
        var pageable = PageConverter.covertToPageable(filter.getPageRequest());
        var spec = CounterpartySpecification.byCounterpartyNameLike(filter.search);
        return counterpartyRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CounterpartyEntity> getCounterpartiesPage(PageRequest request) {
        log.info("invoke CounterpartyServiceImpl#getCounterpartiesPage with request={}", request);
        var pageable = PageConverter.covertToPageable(request);
        return counterpartyRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public CounterpartyEntity createCounterparty(CreateCounterpartyRequest request) {
        log.info("invoke CounterpartyServiceImpl#createCounterparty with request={}", request);
        return counterpartyRepository.save(counterpartyCreateMapper.apply(request));
    }

    @Override
    @Transactional
    public CounterpartyEntity updateCounterparty(UpdateCounterpartyRequest request) {
        log.info("invoke CounterpartyServiceImpl#updateCounterparty with request={}", request);
        var counterparty = findById(request.getId());
        updateCounterpartyByPatch(counterparty, request.getPatch());
        return counterpartyRepository.save(counterparty);
    }

    @Override
    @Transactional
    public void deleteCounterpartyById(UUID counterpartyId) {
        log.info("invoke CounterpartyServiceImpl#deleteCounterpartyById by id={}", counterpartyId);
        counterpartyRepository.deleteById(counterpartyId);
    }

    private void updateCounterpartyByPatch(CounterpartyEntity counterparty, CounterpartyFieldsPatch patch) {
        ofNullable(patch.getFullName()).ifPresent(counterparty::setFullName);
        ofNullable(patch.getName()).ifPresent(counterparty::setName);
        ofNullable(patch.getInn()).ifPresent(counterparty::setInn);
        ofNullable(patch.getEmail()).ifPresent(counterparty::setEmail);
        ofNullable(patch.getPhone()).ifPresent(counterparty::setPhone);
    }
}
