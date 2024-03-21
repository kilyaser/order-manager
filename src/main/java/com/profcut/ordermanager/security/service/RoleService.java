package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;

import java.util.Set;

public interface RoleService {

    OmRoleEntity findByRoleName(OmRole role);

    Set<OmRoleEntity> findRoles(Set<OmRole> roles);

    Set<String> findAll();
}
