package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.domain.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID>, JpaSpecificationExecutor<PaymentEntity> {

    @Query(value = "SELECT p FROM PaymentEntity p WHERE p.paymentId = :paymentId")
    Optional<PaymentEntity> findByPaymentId(@Param("paymentId") UUID paymentId);

    @Query(value = "SELECT p FROM PaymentEntity p WHERE p.order.orderId = :orderId")
    List<PaymentEntity> findAllByOrderId(@Param("orderId") UUID orderId);
}
