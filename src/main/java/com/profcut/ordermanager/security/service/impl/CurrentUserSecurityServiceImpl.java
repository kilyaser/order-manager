package com.profcut.ordermanager.security.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.OmUserMapper;
import com.profcut.ordermanager.domain.dto.auth.OmUser;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentUserSecurityServiceImpl implements CurrentUserSecurityService {

    private final OmUserMapper omUserMapper;

    @Override
    public Optional<String> getLogin() {
        return getUserInfo().map(OmUser::getEmail);
    }

    private Optional<OmUser> getUserInfo() {
        var omUserDb = ((OmUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ofNullable(omUserMapper.apply(omUserDb));
    }
}
