package com.profcut.ordermanager.servcie.impl;

import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.TechnologistFieldsPatch;
import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.UpdateTechnologistRequest;
import com.profcut.ordermanager.controllers.rest.ui.mapper.UiTechnologistCreatorMapper;
import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import com.profcut.ordermanager.domain.exceptions.TechnologistNotFoundException;
import com.profcut.ordermanager.domain.repository.TechnologistRepository;
import com.profcut.ordermanager.servcie.TechnologistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TechnologistServiceImpl implements TechnologistService {

    private final TechnologistRepository technologistRepository;
    private final UiTechnologistCreatorMapper uiTechnologistCreatorMapper;

    @Override
    @Transactional(readOnly = true)
    public TechnologistEntity findByName(String fullName) {
        return technologistRepository.findTechnologistByName(fullName)
                .orElseThrow(() -> TechnologistNotFoundException.byTechnologistName(fullName));
    }

    @Override
    @Transactional
    public TechnologistEntity updateTechnologist(UpdateTechnologistRequest updateRequest) {
        var technologistFromDb = getById(updateRequest.getId());
        updateTechnologistByPatch(technologistFromDb, updateRequest.getPatch());
        return technologistRepository.save(technologistFromDb);
    }

    @Override
    public TechnologistEntity createTechnologist(CreateTechnologistRequest request) {
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
        technologistRepository.deleteById(id);
    }

    private void updateTechnologistByPatch(TechnologistEntity technologist, TechnologistFieldsPatch patch) {
        ofNullable(patch.getFullName()).ifPresent(technologist::setFullName);
        ofNullable(patch.getPhone()).ifPresent(technologist::setPhone);
        ofNullable(patch.getEmail()).ifPresent(technologist::setEmail);
    }
}
