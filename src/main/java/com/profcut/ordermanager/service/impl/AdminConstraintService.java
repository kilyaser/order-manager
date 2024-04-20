package com.profcut.ordermanager.service.impl;


import com.profcut.ordermanager.domain.dto.constraint.AdminConstraint;
import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import com.profcut.ordermanager.service.ConstraintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service("adminConstraintService")
public class AdminConstraintService extends ConstraintService {

    public AdminConstraintService(CurrentUserSecurityService currentUserSecurityService) {
        super(currentUserSecurityService);
    }

    @Transactional(readOnly = true)
    public AdminConstraint getAdminConstraints() {
        var roles = currentUserSecurityService.getOmUserRoles();
        return AdminConstraint.builder()
                .isAllowViewRoles(canViewRoles(roles))
                .isAllowViewAdminConsole(canViewAdminConsole(roles))
                .build();
    }

    @Transactional(readOnly = true)
    public boolean canViewRoles() {
        return hasAllowedRole();
    }

    private boolean canViewRoles(Set<String> roles) {
        return hasAllowedRole(roles);
    }

    private boolean canViewAdminConsole(Set<String> roles) {
        return hasAllowedRole(roles);
    }
}
