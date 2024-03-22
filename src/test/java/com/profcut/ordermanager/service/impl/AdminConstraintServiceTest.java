package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.profcut.ordermanager.security.domain.model.enums.OmRole.CEO;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.MANAGER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminConstraintServiceTest {

    @Mock
    CurrentUserSecurityService currentUserSecurityService;

    @InjectMocks
    AdminConstraintService adminConstraintService;

    @Test
    @DisplayName("Должен устанавливать разрешения в зависимость от наличия роли")
    void getAdminConstraints_allowed() {
        var roles = Set.of(MANAGER.name(), CEO.name());

        when(currentUserSecurityService.getOmUserRoles()).thenReturn(roles);

        var result = adminConstraintService.getAdminConstraints();

        assertNotNull(result);
        assertTrue(result.isAllowViewAdminConsole());
        assertTrue(result.isAllowViewRoles());
    }

    @Test
    @DisplayName("Должен устанавливать разрешения в зависимость от наличия роли")
    void getAdminConstraints_not_allowed() {
        var roles = Set.of(MANAGER.name());

        when(currentUserSecurityService.getOmUserRoles()).thenReturn(roles);

        var result = adminConstraintService.getAdminConstraints();

        assertNotNull(result);
        assertFalse(result.isAllowViewAdminConsole());
        assertFalse(result.isAllowViewRoles());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void canViewRoles(boolean canAllowed) {

        when(currentUserSecurityService.hasAnyRole(any())).thenReturn(canAllowed);

        assertEquals(canAllowed, adminConstraintService.canViewRoles());
    }
}
