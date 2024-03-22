package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;

import java.util.Optional;
import java.util.Set;

public interface CurrentUserSecurityService {

    Optional<String> getLogin();

    Set<String> getOmUserRoles();

    OmUserEntity getOmUserEntity();

    boolean hasAnyRole(Set<String> roles);
}
