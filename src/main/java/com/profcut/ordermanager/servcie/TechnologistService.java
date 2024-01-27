package com.profcut.ordermanager.servcie;

import com.profcut.ordermanager.domain.entities.TechnologistEntity;

public interface TechnologistService {

    TechnologistEntity findTechnologistByName(String fullName);
}
