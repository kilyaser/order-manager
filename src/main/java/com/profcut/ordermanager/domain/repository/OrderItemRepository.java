package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {

    @Modifying
    @Query("DELETE FROM OrderItemEntity WHERE id IN :itemIds")
    void deleteOrderItems(@Param("itemIds") Set<UUID> itemIds);
}
