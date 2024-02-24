package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.controllers.rest.dto.auth.AuthRequest;
import com.profcut.ordermanager.controllers.rest.dto.auth.AuthResponse;
import com.profcut.ordermanager.controllers.rest.dto.auth.RegisterRequest;

public interface AuthenticationService {

    AuthResponse register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest request);
}
