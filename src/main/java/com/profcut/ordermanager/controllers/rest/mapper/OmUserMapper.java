package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.controllers.rest.dto.auth.OmUser;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import org.mapstruct.Mapper;


import java.util.function.Function;

@Mapper(uses = OmRoleMapper.class)
public interface OmUserMapper extends Function<OmUser, OmUserEntity> {

    OmUser apply(OmUserEntity entity);
}
