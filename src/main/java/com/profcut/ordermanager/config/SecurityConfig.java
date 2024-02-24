package com.profcut.ordermanager.config;

import com.profcut.ordermanager.security.domain.model.repository.OmUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OmUserRepository omUserRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> omUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email: %s not found", username)));
    }
}
