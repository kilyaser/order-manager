package com.profcut.ordermanager.controllers.rest.ui.mapper;

import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface UiTechnologistCreatorMapper extends Function<CreateTechnologistRequest, TechnologistEntity> {

    @Override
    TechnologistEntity apply(CreateTechnologistRequest request);
}
