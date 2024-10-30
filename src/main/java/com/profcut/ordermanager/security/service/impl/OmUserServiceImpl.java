package com.profcut.ordermanager.security.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.UpdateOmUserMapper;
import com.profcut.ordermanager.domain.dto.auth.UpdateOmUserRequest;
import com.profcut.ordermanager.domain.exceptions.OmUserNotFoundException;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.domain.model.repository.OmUserRepository;
import com.profcut.ordermanager.security.service.OmUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class OmUserServiceImpl implements OmUserService {

    private final OmUserRepository omUserRepository;
    private final UpdateOmUserMapper updateOmUserMapper;

    @Override
    @Transactional
    public OmUserEntity updateOmUser(UpdateOmUserRequest request) {
        log.info("invoke OmUserServiceImpl#updateOmUser with request: {}", request);
        var omUserDb = findById(() -> omUserRepository.findById(request.getId()), OmUserNotFoundException::byId, request.getId());
        updateOmUserMapper.updateOmUser(request.getPatch(), omUserDb);
        return omUserRepository.save(omUserDb);
    }

    @Override
    @Transactional(readOnly = true)
    public OmUserEntity findById(UUID userId) {
        return findById(() -> omUserRepository.findById(userId), OmUserNotFoundException::byId, userId);
    }

    private <T> T findById(Supplier<Optional<T>> findByIdFunction, Function<UUID, RuntimeException> exception, UUID userId) {
        return findByIdFunction.get().orElseThrow(() -> exception.apply(userId));
    }
}
