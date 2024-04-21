package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.material.CreateMaterialRequest;
import com.profcut.ordermanager.domain.entities.MaterialEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface MaterialCreateMapper extends Function<CreateMaterialRequest, MaterialEntity> {

    @Override
    MaterialEntity apply(CreateMaterialRequest createMaterialRequest);
}
