package com.profcut.ordermanager.controllers.rest.auth;


import com.profcut.ordermanager.controllers.rest.dto.auth.AuthRequest;
import com.profcut.ordermanager.controllers.rest.dto.auth.AuthResponse;
import com.profcut.ordermanager.controllers.rest.dto.auth.RegisterRequest;
import com.profcut.ordermanager.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "authentication-api", description = "Контроллер для регистрации пользователей")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        return null;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request) {
        return null;
    }
}
