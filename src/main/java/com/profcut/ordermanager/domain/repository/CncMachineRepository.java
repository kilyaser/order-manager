package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.domain.entities.CncMachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CncMachineRepository extends JpaRepository<CncMachineEntity, UUID> {
}
