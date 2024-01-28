package com.profcut.ordermanager.servcie.impl;

import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import com.profcut.ordermanager.domain.exceptions.TechnologistNotFoundException;
import com.profcut.ordermanager.domain.repository.TechnologistRepository;
import com.profcut.ordermanager.servcie.TechnologistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TechnologistServiceImpl implements TechnologistService {

    private final TechnologistRepository technologistRepository;

    @Override
    @Transactional(readOnly = true)
    public TechnologistEntity findByName(String fullName) {
        return technologistRepository.findTechnologistByName(fullName)
                .orElseThrow(() -> TechnologistNotFoundException.byTechnologistName(fullName));
    }

    @Override
    public TechnologistEntity save(TechnologistEntity technologist) {
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
}
