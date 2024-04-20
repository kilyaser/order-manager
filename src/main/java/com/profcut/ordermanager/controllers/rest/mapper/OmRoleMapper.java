package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.auth.OmRoleDto;
import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface OmRoleMapper extends Function<OmRoleEntity, OmRoleDto> {

    OmRoleDto apply(OmRoleEntity entity);
}
