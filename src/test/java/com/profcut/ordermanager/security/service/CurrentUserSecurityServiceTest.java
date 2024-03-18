package com.profcut.ordermanager.security.service;

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

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultOmUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void getLogin_success() {
        var user = getDefaultOmUserEntity();

        when(authentication.getPrincipal()).thenReturn(user);

        Optional<String> login = currentUserSecurityService.getLogin();

        assertTrue(login.isPresent());
        assertEquals(user.getUsername(), login.get());
    }

    @Test
    @DisplayName("Успешное получение пользователя")
    public void getOmUserEntity_success() {
        var user = getDefaultOmUserEntity();

        when(authentication.getPrincipal()).thenReturn(user);

        var omUserEntity = currentUserSecurityService.getOmUserEntity();

        assertNotNull(omUserEntity);
        assertEquals(user.getUsername(), omUserEntity.getUsername());
    }

    @Test
    @DisplayName("Пользователь отсутствует в контексте")
    public void getOmUserEntity_failure() {
        when(authentication.getPrincipal()).thenReturn(null);

        assertThrows(AccessDeniedException.class, () -> currentUserSecurityService.getOmUserEntity());
    }
}
