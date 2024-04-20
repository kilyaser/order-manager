package com.profcut.ordermanager.security.service.impl;

import com.profcut.ordermanager.domain.dto.auth.PasswordUpdateRequest;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.domain.model.repository.OmUserRepository;
import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import com.profcut.ordermanager.security.service.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;
    private final OmUserRepository omUserRepository;
    private final CurrentUserSecurityService currentUserSecurityService;


    @Override
    public boolean updatePassword(PasswordUpdateRequest request) {
        var user = currentUserSecurityService.getOmUserEntity();
        log.info("invoke PasswordServiceImpl#updatePassword for user: {}", user.getUsername());
        if (validatePasswordBeforeSave(request, user)) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            omUserRepository.save(user);
            return true;
        }
        return false;
    }

    private boolean validatePasswordBeforeSave(PasswordUpdateRequest request, OmUserEntity user) {
        var currentPass = user.getPassword();
        var oldPass = request.getOldPassword();
        var newPass = request.getNewPassword();

        var oldIsValid = checkIfValidOldPassword(oldPass, currentPass);
        var newIsValid = checkIfValidNewPassword(newPass, currentPass);
        log.info("isOldPassValid: {}, isNewPassValid: {}", oldIsValid, newIsValid);
        return oldIsValid && newIsValid;
    }

    private boolean checkIfValidOldPassword(String oldPass, String currentPass) {
        return passwordEncoder.matches(oldPass, currentPass);
    }

    private boolean checkIfValidNewPassword(String newPassword, String currentPass) {
        return !passwordEncoder.matches(newPassword, currentPass);
    }
}
