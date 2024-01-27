package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TechnologistRepository extends JpaRepository<TechnologistEntity, UUID> {

    @Query(value = "SELECT t from TechnologistEntity t WHERE t.fullName = :fullName")
    Optional<TechnologistEntity> findTechnologistByName(@Param("fullName") String fullName);
}
