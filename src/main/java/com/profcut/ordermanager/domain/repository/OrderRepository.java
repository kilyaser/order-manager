package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.domain.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID>, JpaSpecificationExecutor<OrderEntity> {

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE YEAR(o.createdDate) = YEAR(CURRENT_DATE)")
    int getOrderNumbers();

    @Query("SELECT o FROM OrderEntity o WHERE o.orderId = :orderId")
    Optional<OrderEntity> findOrderById(@Param("orderId") UUID orderId);
}
