package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.controllers.rest.mapper.OmUserCreateMapper;
import com.profcut.ordermanager.controllers.rest.mapper.OmUserMapper;
import com.profcut.ordermanager.security.domain.model.repository.OmUserRepository;
import com.profcut.ordermanager.security.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    }
}
