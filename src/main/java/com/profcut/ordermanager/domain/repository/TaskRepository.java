package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.domain.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {


    @Query(value = "SELECT t FROM TaskEntity t WHERE t.order.orderId = :orderId")
    List<TaskEntity> findAllByOrderId(@Param("orderId") UUID orderId);
}
