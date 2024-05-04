package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.payment.CreatePaymentRequest;
import com.profcut.ordermanager.domain.dto.payment.PaymentFieldsPatch;
import com.profcut.ordermanager.domain.dto.payment.UpdatePaymentRequest;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import com.profcut.ordermanager.domain.exceptions.PaymentNotFoundException;
import com.profcut.ordermanager.domain.repository.PaymentRepository;
import com.profcut.ordermanager.massaging.events.ChangePaymentEvent;
import com.profcut.ordermanager.service.CounterpartyService;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.service.PaymentService;
import com.profcut.ordermanager.utils.jpa.specification.PageConverter;
import com.profcut.ordermanager.utils.jpa.specification.PaymentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CounterpartyService counterpartyService;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderService orderService;

    @Override
    @Transactional
    public PaymentEntity addPayment(CreatePaymentRequest request) {
        log.info("invoke 'PaymentServiceImpl#addPayment' with request: {}", request);
        var counterparty = counterpartyService.findById(request.getCounterpartyId());
        var order = orderService.findOrderById(request.getOrderId());
        var payment = new PaymentEntity()
                .setOrder(order)
                .setCounterparty(counterparty)
                .setPaymentSum(request.getPaymentSum());
        ofNullable(request.getPaymentDate()).ifPresent(payment::setPaymentDate);
        eventPublisher.publishEvent(ChangePaymentEvent.of(request.getOrderId(), payment, false));
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void deletePayment(UUID paymentId) {
        log.info("invoke 'PaymentServiceImpl#deletePayment' with paymentId: {}", paymentId);
        var payment = findPaymentById(paymentId);
        eventPublisher.publishEvent(ChangePaymentEvent.of(payment.getOrder().getOrderId(), payment, true));
        paymentRepository.delete(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentEntity findPaymentById(UUID paymentId) {
        return paymentRepository.findByPaymentId(paymentId).orElseThrow(() -> PaymentNotFoundException.byPaymentId(paymentId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentEntity> findAllPaymentByOrderId(UUID orderId) {
        return paymentRepository.findAllByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentEntity> findAllPaymentByCounterpartyId(UUID counterpartyId, PageRequest pageRequest) {
        var spec = PaymentSpecification.byCounterpartyId(counterpartyId);
        var pageable = PageConverter.covertToPageable(pageRequest);
        return paymentRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentEntity> getPaymentPage(PageRequest pageRequest) {
        var pageable = PageConverter.covertToPageable(pageRequest);
        return paymentRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public PaymentEntity updatePayment(UpdatePaymentRequest request) {
        var payment = findPaymentById(request.getPaymentId());
        updatePaymentByPatch(payment, request.getPatch());
        return paymentRepository.save(payment);
    }

    private void updatePaymentByPatch(PaymentEntity payment, PaymentFieldsPatch patch) {
        ofNullable(patch.getPaymentSum()).ifPresent(sum -> {
            payment.setPaymentSum(sum);
            eventPublisher.publishEvent(ChangePaymentEvent.of(payment.getOrder().getOrderId(), payment, false));
        });
        ofNullable(patch.getPaymentDate()).ifPresent(payment::setPaymentDate);
    }
}
