package com.profcut.ordermanager.security.domain.model.repository;

import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface OmRoleRepository extends JpaRepository<OmRoleEntity, UUID> {

    @Query(value = "SELECT r FROM OmRoleEntity r WHERE r.role = :role")
    Optional<OmRoleEntity> findByRole(@Param("role") OmRole role);

    @Query(value = "SELECT r FROM OmRoleEntity r WHERE r.role IN :roles")
    Optional<Set<OmRoleEntity>> findRolesByRoleIn(@Param("roles") Set<OmRole> roles);

}
