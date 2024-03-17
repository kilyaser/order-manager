package com.profcut.ordermanager.security.service.impl;

import com.profcut.ordermanager.domain.dto.auth.OmUserFieldPatch;
import com.profcut.ordermanager.domain.dto.auth.PasswordUpdateRequest;
import com.profcut.ordermanager.domain.dto.auth.UpdateOmUserRequest;
import com.profcut.ordermanager.domain.exceptions.OmUserNotFoundException;
import com.profcut.ordermanager.domain.exceptions.UpdateOmUserException;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.domain.model.repository.OmUserRepository;
import com.profcut.ordermanager.security.service.OmUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class OmUserServiceImpl implements OmUserService {

    private final OmUserRepository omUserRepository;

    @Override
    @Transactional
    public OmUserEntity updateOmUser(UpdateOmUserRequest request) {
        log.info("invoke OmUserServiceImpl#updateOmUser with request: {}", request);
        var omUserDb = omUserRepository.findById(request.getId()).orElseThrow(() -> OmUserNotFoundException.byId(request.getId()));
        updateOmUserByPatch(omUserDb, request.getPatch());
        return omUserRepository.save(omUserDb);
    }

    @Override
    public void changePassword(PasswordUpdateRequest request) {
        //TODO Реализовать метод
    }

    private void updateOmUserByPatch(OmUserEntity entity, OmUserFieldPatch patch) {
        ofNullable(patch.getFirstName()).ifPresent(entity::setFirstName);
        ofNullable(patch.getLastName()).ifPresent(entity::setLastName);
        ofNullable(patch.getPatronymic()).ifPresent(entity::setPatronymic);
        ofNullable(patch.getEmail()).ifPresent(entity::setEmail);
        ofNullable(patch.getPhone()).ifPresent(entity::setPhone);
        ofNullable(patch.getBirthday()).ifPresent((birthday) -> {
            try {
                entity.setBirthday(new SimpleDateFormat().parse(birthday));
            } catch (ParseException e) {
                throw UpdateOmUserException.byBirthday(patch.getBirthday(), e);
            }
        });
    }
}
