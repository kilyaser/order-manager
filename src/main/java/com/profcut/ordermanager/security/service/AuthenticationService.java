package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.controllers.rest.dto.auth.AuthRequest;
import com.profcut.ordermanager.controllers.rest.dto.auth.AuthResponse;
import com.profcut.ordermanager.controllers.rest.dto.auth.OmUser;
import com.profcut.ordermanager.controllers.rest.dto.auth.RegisterRequest;

import java.security.Principal;

public interface AuthenticationService {

    OmUser register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest request);

    AuthResponse refreshToken(Principal principal);
}
