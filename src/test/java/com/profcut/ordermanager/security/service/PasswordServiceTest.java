package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.domain.dto.auth.PasswordUpdateRequest;
import com.profcut.ordermanager.security.domain.model.repository.OmUserRepository;
import com.profcut.ordermanager.security.service.impl.PasswordServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultOmUserEntity;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    OmUserRepository omUserRepository;
    @Mock
    CurrentUserSecurityService currentUserSecurityService;
    @InjectMocks
    PasswordServiceImpl passwordService;

    @Test
    @DisplayName("Успешное обновление юзера")
    public void testUpdatePassword_success() {
        var request = new PasswordUpdateRequest("oldPassword", "newPassword");
        var user = getDefaultOmUserEntity();
        user.setPassword(passwordEncoder.encode("oldPassword"));

        when(currentUserSecurityService.getOmUserEntity()).thenReturn(user);
        when(passwordEncoder.matches(request.getOldPassword(), user.getPassword())).thenReturn(true);

        var result = passwordService.updatePassword(request);

        assertTrue(result);
        verify(omUserRepository).save(user);
    }

    @Test
    @DisplayName("Неуспешное обновление юзера")
    public void testUpdatePassword_failure() {
        var request = new PasswordUpdateRequest( "wrongPassword", "newPassword");
        var user = getDefaultOmUserEntity();
        user.setPassword(passwordEncoder.encode("oldPassword"));

        when(currentUserSecurityService.getOmUserEntity()).thenReturn(user);
        when(passwordEncoder.matches(request.getOldPassword(), user.getPassword())).thenReturn(false);

        var result = passwordService.updatePassword(request);

        assertFalse(result);
        verify(omUserRepository, never()).save(user);
    }
}
