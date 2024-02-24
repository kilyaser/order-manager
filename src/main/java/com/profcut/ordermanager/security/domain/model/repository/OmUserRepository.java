package com.profcut.ordermanager.security.domain.model.repository;

import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OmUserRepository extends JpaRepository<OmUserEntity, UUID> {

    Optional<OmUserEntity> findByEmail(String email);
}
