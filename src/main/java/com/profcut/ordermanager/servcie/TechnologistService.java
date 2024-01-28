package com.profcut.ordermanager.servcie;

import com.profcut.ordermanager.domain.entities.TechnologistEntity;

import java.util.UUID;

public interface TechnologistService {

    TechnologistEntity findByName(String fullName);

    TechnologistEntity save(TechnologistEntity technologist);

    TechnologistEntity getById(UUID id);

    void deleteById(UUID id);
}
