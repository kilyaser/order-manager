package com.profcut.ordermanager.servcie;

import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.UpdateTechnologistRequest;
import com.profcut.ordermanager.domain.entities.TechnologistEntity;

import java.util.UUID;

public interface TechnologistService {

    TechnologistEntity findByName(String fullName);

    TechnologistEntity updateTechnologist(UpdateTechnologistRequest updateRequest);

    TechnologistEntity createTechnologist(CreateTechnologistRequest request);

    TechnologistEntity getById(UUID id);

    void deleteById(UUID id);
}
