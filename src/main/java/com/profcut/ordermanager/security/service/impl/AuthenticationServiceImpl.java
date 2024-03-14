package com.profcut.ordermanager.security.service.impl;

import com.profcut.ordermanager.domain.dto.auth.AuthRequest;
import com.profcut.ordermanager.domain.dto.auth.AuthResponse;
import com.profcut.ordermanager.domain.dto.auth.OmUser;
import com.profcut.ordermanager.domain.dto.auth.RegisterRequest;
import com.profcut.ordermanager.controllers.rest.mapper.OmUserCreateMapper;
import com.profcut.ordermanager.controllers.rest.mapper.OmUserMapper;
import com.profcut.ordermanager.domain.exceptions.OmUserNotFoundException;
import com.profcut.ordermanager.security.domain.model.repository.OmUserRepository;
import com.profcut.ordermanager.security.service.AuthenticationService;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final OmUserRepository omUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final OmUserCreateMapper omUserCreateMapper;
    private final RoleService roleService;
    private final JwtUserService jwtUserService;
    private final AuthenticationManager authenticationManager;
    private final OmUserMapper omUserMapper;

    @Override
    public OmUser register(RegisterRequest request) {
        log.info("invoke AuthenticationServiceImpl#register with request: {}", request);
        var user = omUserCreateMapper.apply(request);
        user.setRoles(roleService.findRoles(request.getRoles()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = omUserRepository.save(user);
        var userDto = omUserMapper.apply(user);
        return omUserMapper.mapRoles(user, userDto);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = omUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> OmUserNotFoundException.byEmail(request.getEmail()));
        var jwtToken = jwtUserService.generateToken(user);
        var refreshToken = jwtUserService.generateRefreshToken(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse refreshToken(Principal principal) {
        var userEntity = omUserRepository.findByEmail(principal.getName())
                .orElseThrow(() -> OmUserNotFoundException.byEmail(principal.getName()));
        var jwtToken = jwtUserService.generateToken(userEntity);
        var refreshToken = jwtUserService.generateRefreshToken(userEntity);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}
