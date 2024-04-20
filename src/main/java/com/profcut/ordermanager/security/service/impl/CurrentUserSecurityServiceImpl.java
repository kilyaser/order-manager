package com.profcut.ordermanager.security.service.impl;

import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentUserSecurityServiceImpl implements CurrentUserSecurityService {

    @Override
    public String getLogin() {
        return getOmUserEntity().getUsername();
    }

    @Override
    public Set<String> getOmUserRoles() {
        return getOmUserEntity().getRoles().stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet());
    }

    @Override
    public OmUserEntity getOmUserEntity() {
        return getUserInfo().orElseThrow(() -> new AccessDeniedException("NO_ACCESS"));
    }

    @Override
    public boolean hasAnyRole(Set<String> roles) {
        var userRoles = getOmUserEntity().getRoles().stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toSet());
        return roles.stream().anyMatch(userRoles::contains);
    }

    private Optional<OmUserEntity> getUserInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || Objects.isNull(authentication.getPrincipal())) {
            return Optional.empty();
        }
        var omUserDb = ((OmUserEntity) authentication.getPrincipal());
        return Optional.of(omUserDb);
    }
}
