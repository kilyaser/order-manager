package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.auth.OmUserFieldPatch;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


import java.time.LocalDate;

@Mapper(imports = LocalDate.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateOmUserMapper {

    void updateOmUser(OmUserFieldPatch patch, @MappingTarget OmUserEntity target);
}
