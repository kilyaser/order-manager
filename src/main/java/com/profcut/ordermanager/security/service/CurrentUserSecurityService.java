package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;

import java.util.Set;

public interface CurrentUserSecurityService {

    String getLogin();

    Set<String> getOmUserRoles();

    OmUserEntity getOmUserEntity();

    boolean hasAnyRole(Set<String> roles);
}
