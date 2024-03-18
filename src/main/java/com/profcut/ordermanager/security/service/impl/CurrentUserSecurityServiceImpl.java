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

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentUserSecurityServiceImpl implements CurrentUserSecurityService {

    @Override
    public Optional<String> getLogin() {
        return getUserInfo().map(OmUserEntity::getUsername);
    }

    @Override
    public OmUserEntity getOmUserEntity() {
        return getUserInfo().orElseThrow(() -> new AccessDeniedException("NO_ACCESS"));
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
