package com.profcut.ordermanager.service;

import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import lombok.AllArgsConstructor;

import java.util.Set;

import static com.profcut.ordermanager.security.domain.model.enums.OmRole.ADMIN;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.CEO;

@AllArgsConstructor
public abstract class ConstraintService {

    protected final CurrentUserSecurityService currentUserSecurityService;

    private final Set<String> allowedRoles = Set.of(ADMIN.name(), CEO.name());

    protected boolean hasAllowedRole(Set<String> roles) {
        return allowedRoles.stream().anyMatch(roles::contains);
    }

    protected boolean hasAllowedRole() {
        return currentUserSecurityService.hasAnyRole(allowedRoles);
    }
}
