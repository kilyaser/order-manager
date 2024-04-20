package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.domain.dto.technologist.UpdateTechnologistRequest;
import com.profcut.ordermanager.domain.entities.TechnologistEntity;

import java.util.UUID;

public interface TechnologistService {

    TechnologistEntity updateTechnologist(UpdateTechnologistRequest updateRequest);

    TechnologistEntity createTechnologist(CreateTechnologistRequest request);

    TechnologistEntity getById(UUID id);

    void deleteById(UUID id);
}
