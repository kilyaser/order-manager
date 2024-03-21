package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.controllers.rest.mapper.OmUserCreateMapper;
import com.profcut.ordermanager.controllers.rest.mapper.OmUserMapper;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.domain.model.repository.OmUserRepository;
import com.profcut.ordermanager.security.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.UUID;

import static com.profcut.ordermanager.security.domain.model.enums.OmRole.MANAGER;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultAuthRequest;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultOmUser;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultOmUserEntity;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultRegisterRequest;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getSelectedRole;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    OmUserRepository omUserRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    OmUserCreateMapper omUserCreateMapper;
    @Mock
    RoleService roleService;
    @Mock
    JwtUserService jwtUserService;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    OmUserMapper omUserMapper;

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @Test
    void register() {
        var id = UUID.randomUUID();
        var request = getDefaultRegisterRequest();
        var omUserEntity = getDefaultOmUserEntity();
        omUserEntity.setId(id).setRoles(getSelectedRole(MANAGER));
        var omUser = getDefaultOmUser(request, id);

        when(omUserCreateMapper.apply(request)).thenReturn(omUserEntity);
        when(roleService.findRoles(any())).thenReturn(getSelectedRole(MANAGER));
        when(omUserRepository.save(any())).thenReturn(omUserEntity);
        when(omUserMapper.apply(any(OmUserEntity.class))).thenReturn(omUser);

        assertThatCode(() -> authenticationService.register(request)).doesNotThrowAnyException();

        verify(omUserMapper).mapRoles(any(), any());
        verify(omUserRepository).save(any());
        verify(omUserMapper).apply(any(OmUserEntity.class));
        verify(passwordEncoder).encode(any());
    }

    @Test
    void authenticate() {
        var request = getDefaultAuthRequest();
        var userEntity = getDefaultOmUserEntity();

        when(omUserRepository.findByEmail(any())).thenReturn(of(userEntity));

        assertThatCode(() -> authenticationService.authenticate(request)).doesNotThrowAnyException();

        verify(authenticationManager).authenticate(any());
        verify(omUserRepository).findByEmail(any());
        verify(jwtUserService).generateToken(any());
        verify(jwtUserService).generateRefreshToken(any());
    }

    @Test
    void refreshToken() {
        Principal principal = () -> "test@mail.ru";
        var userEntity = getDefaultOmUserEntity();

        when(omUserRepository.findByEmail(principal.getName())).thenReturn(of(userEntity));

        assertThatCode(() -> authenticationService.refreshToken(principal)).doesNotThrowAnyException();

        verify(omUserRepository).findByEmail(any());
        verify(jwtUserService).generateToken(any());
        verify(jwtUserService).generateRefreshToken(any());
    }
}
