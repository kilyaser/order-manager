package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.payment.CreatePaymentRequest;
import com.profcut.ordermanager.domain.dto.payment.PaymentFieldsPatch;
import com.profcut.ordermanager.domain.dto.payment.UpdatePaymentRequest;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import com.profcut.ordermanager.domain.exceptions.PaymentNotFoundException;
import com.profcut.ordermanager.domain.repository.PaymentRepository;
import com.profcut.ordermanager.massaging.events.ChangePaymentEvent;
import com.profcut.ordermanager.service.CounterpartyService;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    PaymentRepository paymentRepository;
    @Mock
    CounterpartyService counterpartyService;
    @Mock
    OrderService orderService;
    @Mock
    ApplicationEventPublisher eventPublisher;

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Test
    @DisplayName("Добавление платежа")
    void addPayment() {
        var request = CreatePaymentRequest.builder()
                .counterpartyId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .paymentSum(BigDecimal.valueOf(1000))
                .build();
        var order = TestDataHelper.buildDefaultOrder();
        var counterparty = new CounterpartyEntity().setId(UUID.randomUUID()).setName("counterparty");

        when(counterpartyService.findById(request.getCounterpartyId())).thenReturn(counterparty);
        when(orderService.findOrderById(any())).thenReturn(order);

        var eventCaptor = ArgumentCaptor.forClass(ChangePaymentEvent.class);
        var paymentCaptor = ArgumentCaptor.forClass(PaymentEntity.class);

        assertThatCode(() -> paymentService.addPayment(request)).doesNotThrowAnyException();

        verify(eventPublisher).publishEvent(eventCaptor.capture());
        verify(paymentRepository).save(paymentCaptor.capture());
        verify(orderService).findOrderById(any(UUID.class));

        assertThat(eventCaptor.getValue())
                .isInstanceOf(ChangePaymentEvent.class)
                .satisfies(event -> {
                    assertThat(event.getOrderId()).isEqualTo(request.getOrderId());
                    assertThat(event.getPayment().getPaymentSum()).isEqualTo(request.getPaymentSum());
                    assertFalse(event.isDelete());
                });
        assertThat(paymentCaptor.getValue())
                .isInstanceOf(PaymentEntity.class)
                .satisfies(payment -> {
                    assertThat(payment.getCounterparty()).isEqualTo(counterparty);
                    assertThat(payment.getPaymentSum()).isEqualTo(request.getPaymentSum());
                });
    }

    @Test
    @DisplayName("Удаление платежа по id")
    void deletePayment() {
        var id = UUID.randomUUID();
        var order = TestDataHelper.buildDefaultOrder();
        var payment = new PaymentEntity()
                .setPaymentId(id)
                .setPaymentSum(BigDecimal.valueOf(5000))
                .setOrder(order);
        var captor = ArgumentCaptor.forClass(ChangePaymentEvent.class);

        when(paymentRepository.findByPaymentId(id)).thenReturn(Optional.of(payment));

        assertThatCode(() -> paymentService.deletePayment(id)).doesNotThrowAnyException();

        verify(eventPublisher).publishEvent(captor.capture());
        verify(paymentRepository).delete(any(PaymentEntity.class));
        verify(paymentRepository).findByPaymentId(id);

        assertThat(captor.getValue())
                .isInstanceOf(ChangePaymentEvent.class)
                .satisfies(event -> {
                    assertThat(event.getOrderId()).isEqualTo(order.getOrderId());
                    assertThat(event.getPayment()).isEqualTo(payment);
                    assertTrue(event.isDelete());
                });
    }

    @Test
    @DisplayName("Поиск платежа по id. Успех")
    void findPaymentById() {
        var id = UUID.randomUUID();
        var payment = new PaymentEntity()
                .setPaymentId(id)
                .setPaymentSum(BigDecimal.valueOf(5000))
                .setOrder(TestDataHelper.buildDefaultOrder());

        when(paymentRepository.findByPaymentId(id)).thenReturn(Optional.of(payment));

        assertThatCode(() -> paymentService.findPaymentById(id)).doesNotThrowAnyException();

        verify(paymentRepository).findByPaymentId(id);
    }

    @Test
    @DisplayName("Поиск платежа по id. Ошибка поиска")
    void findPaymentById_exception() {
        var id = UUID.randomUUID();

        when(paymentRepository.findByPaymentId(id)).thenReturn(Optional.empty());

        assertThatCode(() -> paymentService.findPaymentById(id)).isInstanceOf(PaymentNotFoundException.class);

        verify(paymentRepository).findByPaymentId(id);
    }

    @Test
    @DisplayName("Получение списка платежей по id заказа")
    void findAllPaymentByOrderId() {
        var orderId = UUID.randomUUID();

        assertThatCode(() -> paymentService.findAllPaymentByOrderId(orderId)).doesNotThrowAnyException();

        verify(paymentRepository).findAllByOrderId(orderId);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Найти все платежи по контрагенту")
    void findAllPaymentByCounterpartyId() {
        var counterpartyId = UUID.randomUUID();
        var pageRequest = new PageRequest().setPage(0).setSize(20);

        assertThatCode(() -> paymentService.findAllPaymentByCounterpartyId(counterpartyId, pageRequest)).doesNotThrowAnyException();

        verify(paymentRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Обновление платежа")
    void updatePayment() {
        var id = UUID.randomUUID();
        var order = TestDataHelper.buildDefaultOrder();
        var payment = new PaymentEntity()
                .setPaymentId(id)
                .setPaymentSum(BigDecimal.valueOf(5000))
                .setOrder(order);
        var request = new UpdatePaymentRequest().setPaymentId(id)
                .setPatch(new PaymentFieldsPatch()
                        .setPaymentSum(BigDecimal.valueOf(1000))
                        .setPaymentDate(LocalDateTime.now()));
        var eventCaptor = ArgumentCaptor.forClass(ChangePaymentEvent.class);
        var paymentCaptor = ArgumentCaptor.forClass(PaymentEntity.class);

        when(paymentRepository.findByPaymentId(id)).thenReturn(Optional.of(payment));

        assertThatCode(() -> paymentService.updatePayment(request)).doesNotThrowAnyException();

        verify(eventPublisher).publishEvent(eventCaptor.capture());
        verify(paymentRepository).save(paymentCaptor.capture());

        assertThat(eventCaptor.getValue())
                .isInstanceOf(ChangePaymentEvent.class)
                .satisfies(event -> {
                    assertThat(event.getOrderId()).isEqualTo(payment.getOrder().getOrderId());
                    assertThat(event.getPayment().getPaymentSum()).isEqualTo(request.getPatch().getPaymentSum());
                    assertFalse(event.isDelete());
                });
        assertThat(paymentCaptor.getValue())
                .isInstanceOf(PaymentEntity.class)
                .satisfies(p -> {
                    assertThat(p.getPaymentId()).isEqualTo(payment.getPaymentId());
                    assertThat(p.getPaymentSum()).isEqualTo(request.getPatch().getPaymentSum());
                });
    }
}
