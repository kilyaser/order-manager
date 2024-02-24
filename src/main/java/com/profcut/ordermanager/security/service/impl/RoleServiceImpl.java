package com.profcut.ordermanager.security.service.impl;

import com.profcut.ordermanager.domain.exceptions.OmRoleNotFoundException;
import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import com.profcut.ordermanager.security.domain.model.repository.OmRoleRepository;
import com.profcut.ordermanager.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final OmRoleRepository omRoleRepository;

    @Override
    public OmRoleEntity findByRoleName(OmRole role) {
        return omRoleRepository.findByRole(role).orElseThrow(() -> OmRoleNotFoundException.byRoleName(role.name()));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<OmRoleEntity> findRoles(Set<OmRole> roles) {
        return omRoleRepository.findRolesByRoleIn(roles)
                .filter(roleNames -> roleNames.size() == roles.size())
                .orElseThrow(() -> new OmRoleNotFoundException("Incorrect roles: " + roles));
    }
}
