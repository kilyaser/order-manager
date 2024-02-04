package com.profcut.ordermanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String GROUPS = "groups";
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(c -> c.requestMatchers("/order-manager-api", "/index.html",
                                "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**").permitAll()
                        .requestMatchers("/ui/technologists").hasRole("MANAGER")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        var converter = new JwtAuthenticationConverter();
//        var jwtGrantedAuthoritiesConverter = new JwtAuthenticationConverter();
//        converter.setPrincipalClaimName("preferred_username");
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt).getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority).toList();
//            var roles = (List<String>)jwt.getClaimAsMap(REALM_ACCESS_CLAIM).get(ROLES_CLAIM);
//            return Stream.concat(authorities.stream(), roles.stream())
//                    .filter(role -> role.startsWith("ROLE_"))
//                    .map(SimpleGrantedAuthority::new)
//                    .map(GrantedAuthority.class::cast).toList();
//        });
//        return converter;
//    }
}
