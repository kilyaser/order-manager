package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.domain.dto.auth.PasswordUpdateRequest;

public interface PasswordService {

    boolean updatePassword(PasswordUpdateRequest request);
}
