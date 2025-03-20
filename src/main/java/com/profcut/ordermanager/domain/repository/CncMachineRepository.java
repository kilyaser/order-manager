package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.domain.entities.CncMachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CncMachineRepository extends JpaRepository<CncMachineEntity, UUID> {

    @Query("SELECT m FROM CncMachineEntity m WHERE m.id IN :machineIds")
    List<CncMachineEntity> findAllByMachineIds(@Param("machineIds") Set<UUID> machineIds);

    List<CncMachineEntity> findCncMachineEntitiesByOrderItemId(UUID orderItemId);
}
