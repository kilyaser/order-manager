package com.profcut.ordermanager.controllers.rest.ui.mapper;

import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.UiTechnologist;
import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import org.mapstruct.Mapper;


import java.util.function.Function;

@Mapper
public interface UiTechnologistMapper extends Function<TechnologistEntity, UiTechnologist> {

    @Override
    UiTechnologist apply(TechnologistEntity technologist);
}
