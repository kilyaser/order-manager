package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CounterpartyRepository extends JpaRepository<CounterpartyEntity, UUID>, JpaSpecificationExecutor<CounterpartyEntity> {

    @Query(value = "SELECT c FROM CounterpartyEntity c WHERE c.id = :id")
    Optional<CounterpartyEntity> findCounterpartyById(@Param("id") UUID id);
}
