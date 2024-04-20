package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.domain.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.productId = :productId")
    Optional<ProductEntity> findByProductId(@Param("productId") UUID productId);
}
