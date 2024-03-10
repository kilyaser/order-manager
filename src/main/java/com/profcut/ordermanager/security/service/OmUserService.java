package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.controllers.rest.dto.auth.UpdateOmUserRequest;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;

public interface OmUserService {

    OmUserEntity updateOmUser(UpdateOmUserRequest request);
}