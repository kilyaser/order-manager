package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.controllers.rest.dto.auth.AuthRequest;
import com.profcut.ordermanager.controllers.rest.dto.auth.AuthResponse;
import com.profcut.ordermanager.controllers.rest.dto.auth.RegisterRequest;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;

public interface AuthenticationService {

    OmUserEntity register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest request);
}
