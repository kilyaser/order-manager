package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.material.UiMaterial;
import com.profcut.ordermanager.domain.entities.MaterialEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface UiMaterialMapper extends Function<MaterialEntity, UiMaterial> {

    @Override
    UiMaterial apply(MaterialEntity entity);
}
