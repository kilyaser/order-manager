package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.domain.dto.auth.UpdateOmUserRequest;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;

import java.util.UUID;

public interface OmUserService {

    OmUserEntity updateOmUser(UpdateOmUserRequest request);

    OmUserEntity findById(UUID userId);
}
