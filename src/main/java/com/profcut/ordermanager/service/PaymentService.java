package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.payment.CreatePaymentRequest;
import com.profcut.ordermanager.domain.dto.payment.UpdatePaymentRequest;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    PaymentEntity addPayment(CreatePaymentRequest request);

    void deletePayment(UUID paymentId);

    PaymentEntity findPaymentById(UUID paymentId);

    List<PaymentEntity> findAllPaymentByOrderId(UUID orderId);

    Page<PaymentEntity> findAllPaymentByCounterpartyId(UUID counterpartyId, PageRequest pageRequest);

    Page<PaymentEntity> getPaymentPage(PageRequest pageRequest);

    PaymentEntity updatePayment(UpdatePaymentRequest request);
}
