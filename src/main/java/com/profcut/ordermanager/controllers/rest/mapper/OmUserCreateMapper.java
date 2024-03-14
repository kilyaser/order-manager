package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.auth.RegisterRequest;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.function.Function;

@Mapper
public interface OmUserCreateMapper extends Function<RegisterRequest, OmUserEntity> {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    OmUserEntity apply(RegisterRequest request);
}
