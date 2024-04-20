package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.domain.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.domain.dto.technologist.TechnologistFieldsPatch;
import com.profcut.ordermanager.domain.dto.technologist.UpdateTechnologistRequest;
import com.profcut.ordermanager.controllers.rest.mapper.UiTechnologistCreatorMapper;
import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import com.profcut.ordermanager.domain.exceptions.TechnologistNotFoundException;
import com.profcut.ordermanager.domain.repository.TechnologistRepository;
import com.profcut.ordermanager.service.TechnologistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class TechnologistServiceImpl implements TechnologistService {

    private final TechnologistRepository technologistRepository;
    private final UiTechnologistCreatorMapper uiTechnologistCreatorMapper;

    @Override
    @Transactional
    public TechnologistEntity updateTechnologist(UpdateTechnologistRequest updateRequest) {
        log.info("invoke TechnologistServiceImpl#updateTechnologist with updateRequest: {}", updateRequest);
        var technologistFromDb = getById(updateRequest.getId());
        updateTechnologistByPatch(technologistFromDb, updateRequest.getPatch());
        return technologistRepository.save(technologistFromDb);
    }

    @Override
    public TechnologistEntity createTechnologist(CreateTechnologistRequest request) {
        log.info("invoke TechnologistServiceImpl#createTechnologist with request: {}", request);
        var technologist = uiTechnologistCreatorMapper.apply(request);
        return technologistRepository.save(technologist);
    }

    @Override
    @Transactional(readOnly = true)
    public TechnologistEntity getById(UUID id) {
        return technologistRepository.findByTechnologistId(id)
                .orElseThrow(() -> TechnologistNotFoundException.byTechnologistId(id));
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        log.info("invoke TechnologistServiceImpl#deleteById with id: {}", id);
        technologistRepository.deleteById(id);
    }

    private void updateTechnologistByPatch(TechnologistEntity technologist, TechnologistFieldsPatch patch) {
        ofNullable(patch.getFirstName()).ifPresent(technologist::setFirstName);
        ofNullable(patch.getLastName()).ifPresent(technologist::setLastName);
        ofNullable(patch.getPatronymic()).ifPresent(technologist::setPatronymic);
        ofNullable(patch.getPhone()).ifPresent(technologist::setPhone);
        ofNullable(patch.getEmail()).ifPresent(technologist::setEmail);
    }
}
