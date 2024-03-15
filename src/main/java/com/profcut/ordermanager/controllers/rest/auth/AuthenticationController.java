package com.profcut.ordermanager.controllers.rest.auth;

import com.profcut.ordermanager.domain.dto.auth.AuthRequest;
import com.profcut.ordermanager.domain.dto.auth.AuthResponse;
import com.profcut.ordermanager.domain.dto.auth.OmUser;
import com.profcut.ordermanager.domain.dto.auth.RegisterRequest;
import com.profcut.ordermanager.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "authentication-api", description = "Контроллер для регистрации пользователей")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public OmUser register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(Principal principal) {
        return ResponseEntity.ok(authService.refreshToken(principal));
    }
}
