package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.controllers.rest.dto.auth.OmUser;
import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper
public interface OmUserMapper extends Function<OmUser, OmUserEntity> {

    @Mapping(target = "roles", ignore = true)
    OmUser apply(OmUserEntity entity);

     default OmUser mapRoles(OmUserEntity entity, OmUser omUser) {
        var roles = entity.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toSet());
        omUser.setRoles(roles);
        return  omUser;
    }

    default Set<OmRoleEntity> map(Set<String> roles) {
        return roles.stream()
                .map(roleName -> new OmRoleEntity().setRole(OmRole.valueOf(roleName)))
                .collect(Collectors.toSet());
    }
}
