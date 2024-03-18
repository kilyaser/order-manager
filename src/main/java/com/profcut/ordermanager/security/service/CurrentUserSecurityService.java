package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;

import java.util.Optional;

public interface CurrentUserSecurityService {

    Optional<String> getLogin();

    OmUserEntity getOmUserEntity();
}
