package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.domain.entities.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<ContractEntity, UUID> {

    @Query(value = "SELECT c FROM ContractEntity c WHERE c.counterparty.id = :id")
    List<ContractEntity> findByCounterpartyId(@Param("id") UUID counterpartyId);
}
