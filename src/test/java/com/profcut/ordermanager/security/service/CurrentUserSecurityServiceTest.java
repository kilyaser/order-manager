package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import com.profcut.ordermanager.security.service.impl.CurrentUserSecurityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.Set;

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultOmUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrentUserSecurityServiceTest {

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CurrentUserSecurityServiceImpl currentUserSecurityService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Успешное получение логина пользователя")
    void getLogin_success() {
        var user = getDefaultOmUserEntity();

        when(authentication.getPrincipal()).thenReturn(user);

        var login = currentUserSecurityService.getLogin();

        assertEquals(user.getUsername(), login);
    }

    @Test
    @DisplayName("Успешное получение списка ролей пользователя")
    void getOmUserRoles_success() {
        var user = getDefaultOmUserEntity();
        var role = new OmRoleEntity().setRole(OmRole.MANAGER);
        user.setRoles(Set.of(role));

        when(authentication.getPrincipal()).thenReturn(user);

        var result = currentUserSecurityService.getOmUserRoles();

        assertNotNull(result);
        assertEquals(user.getRoles().size(), result.size());
    }

    @Test
    @DisplayName("Успешное получение пользователя")
    void getOmUserEntity_success() {
        var user = getDefaultOmUserEntity();

        when(authentication.getPrincipal()).thenReturn(user);

        var omUserEntity = currentUserSecurityService.getOmUserEntity();

        assertNotNull(omUserEntity);
        assertEquals(user.getUsername(), omUserEntity.getUsername());
    }

    @Test
    @DisplayName("Пользователь отсутствует в контексте")
    void getOmUserEntity_failure() {
        when(authentication.getPrincipal()).thenReturn(null);

        assertThrows(AccessDeniedException.class, () -> currentUserSecurityService.getOmUserEntity());
    }

    @Test
    @DisplayName("Проверка наличия роли. Роль имеется.")
    void hasAnyRole_true() {
        var rolesForCheck = Set.of(OmRole.CEO.name());
        var user = getDefaultOmUserEntity();
        var role = new OmRoleEntity().setRole(OmRole.CEO);
        user.setRoles(Set.of(role));

        when(authentication.getPrincipal()).thenReturn(user);

        var result = currentUserSecurityService.hasAnyRole(rolesForCheck);

        assertTrue(result);
    }

    @Test
    @DisplayName("Проверка наличия роли. Роль не имеется.")
    void hasAnyRole_false() {
        var rolesForCheck = Set.of(OmRole.CEO.name());
        var user = getDefaultOmUserEntity();
        var role = new OmRoleEntity().setRole(OmRole.MANAGER);
        user.setRoles(Set.of(role));

        when(authentication.getPrincipal()).thenReturn(user);

        var result = currentUserSecurityService.hasAnyRole(rolesForCheck);

        assertFalse(result);
    }
}
