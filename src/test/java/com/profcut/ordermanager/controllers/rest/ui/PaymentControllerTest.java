package com.profcut.ordermanager.controllers.rest.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.controllers.rest.mapper.UiPaymentMapper;
import com.profcut.ordermanager.controllers.rest.mapper.UiPaymentShortMapper;
import com.profcut.ordermanager.domain.dto.counterparty.UiCounterpartyShort;
import com.profcut.ordermanager.domain.dto.payment.CreatePaymentRequest;
import com.profcut.ordermanager.domain.dto.payment.PaymentFieldsPatch;
import com.profcut.ordermanager.domain.dto.payment.UiPayment;
import com.profcut.ordermanager.domain.dto.payment.UpdatePaymentRequest;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import com.profcut.ordermanager.domain.exceptions.PaymentNotFoundException;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.service.PaymentService;
import com.profcut.ordermanager.utils.jpa.specification.PageConverter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@Import(ErrorHttpResponseFactory.class)
@WithMockUser(username = "user")
public class PaymentControllerTest {

    @MockBean
    PaymentService paymentService;
    @MockBean
    UiPaymentMapper uiPaymentMapper;
    @MockBean
    UiPaymentShortMapper uiPaymentShortMapper;
    @MockBean
    JwtUserService jwtUserService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    @DisplayName("Добавление платежа")
    void addPayment() {
        var request = CreatePaymentRequest.builder()
                .counterpartyId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .paymentSum(BigDecimal.valueOf(1000))
                .build();
        var payment = new PaymentEntity().setCounterparty(new CounterpartyEntity()
                .setId(UUID.randomUUID())
                .setName("Name"));
        var uiPayment = new UiPayment()
                .setCounterparty(new UiCounterpartyShort().setId(UUID.randomUUID()));

        when(paymentService.addPayment(request)).thenReturn(payment);
        when(uiPaymentMapper.apply(any())).thenReturn(uiPayment);

        mockMvc.perform(post("/api/v1/ui/payments", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isCreated(),
                        content().string(objectMapper.writeValueAsString(uiPayment))
                );

        verify(paymentService).addPayment(request);
        verify(uiPaymentMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Добавление платежа. Ошибка валидации")
    void addPayment_exception() {
        var request = CreatePaymentRequest.builder()
                .counterpartyId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .build();

        mockMvc.perform(post("/api/v1/ui/payments", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(paymentService, never()).addPayment(any());
        verify(uiPaymentMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Получения платежа по id")
    void getPayment() {
        var id = UUID.randomUUID();
        var payment = new PaymentEntity().setPaymentId(UUID.randomUUID());
        UiPayment uiPayment = new UiPayment();

        when(paymentService.findPaymentById(id)).thenReturn(payment);
        when(uiPaymentMapper.apply(payment)).thenReturn(uiPayment);

        mockMvc.perform(get("/api/v1/ui/payments/{paymentId}", id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(uiPayment))
                );

        verify(paymentService).findPaymentById(id);
        verify(uiPaymentMapper).apply(payment);
    }

    @Test
    @SneakyThrows
    @DisplayName("Получения платежа по id. Платеж не найден.")
    void getPayment_exception() {
        var id = UUID.randomUUID();

        when(paymentService.findPaymentById(id)).thenThrow(PaymentNotFoundException.class);

        mockMvc.perform(get("/api/v1/ui/payments/{paymentId}", id))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()),
                        jsonPath("$.exClass").value("PaymentNotFoundException"));

        verify(paymentService).findPaymentById(id);
        verify(uiPaymentMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Удаление платежа по id")
    void deletePayment() {
        var paymentId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/ui/payments/{paymentId}", paymentId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(paymentService).deletePayment(paymentId);
    }

    @Test
    @SneakyThrows
    @DisplayName("Получить платежи по id Заказа")
    void getAllPaymentByOrder() {
        var orderId = UUID.randomUUID();
        var payments = List.of(new PaymentEntity().setPaymentId(UUID.randomUUID()),
                new PaymentEntity().setPaymentId(UUID.randomUUID()));

        when(paymentService.findAllPaymentByOrderId(orderId)).thenReturn(payments);

        mockMvc.perform(get("/api/v1/ui/payments/orders/{orderId}", orderId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(paymentService).findAllPaymentByOrderId(orderId);
        verify(uiPaymentShortMapper, times(2)).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Получить платежи по id контрагента")
    void getAllPaymentByCounterparty() {
        var counterpartyId = UUID.randomUUID();
        var payments = List.of(new PaymentEntity().setPaymentId(UUID.randomUUID()),
                new PaymentEntity().setPaymentId(UUID.randomUUID()));
        var pageRequest = new com.profcut.ordermanager.domain.dto.filter.PageRequest()
                .setPage(0).setSize(20);
        var request = PageConverter.covertToPageable(pageRequest);
        Page<PaymentEntity> paymentsPage = new PageImpl<>(payments, request, payments.size());

        when(paymentService.findAllPaymentByCounterpartyId(counterpartyId, pageRequest)).thenReturn(paymentsPage);

        mockMvc.perform(post("/api/v1/ui/payments/counterparty/{counterpartyId}", counterpartyId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pageRequest)))
                .andExpect(status().isOk());

        verify(paymentService).findAllPaymentByCounterpartyId(counterpartyId, pageRequest);
        verify(uiPaymentShortMapper, times(2)).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Получить страницу платежей")
    void getPaymentPage() {
        var payments = List.of(new PaymentEntity().setPaymentId(UUID.randomUUID()),
                new PaymentEntity().setPaymentId(UUID.randomUUID()));
        var pageRequest = new com.profcut.ordermanager.domain.dto.filter.PageRequest()
                .setPage(0).setSize(20);
        var request = PageConverter.covertToPageable(pageRequest);
        Page<PaymentEntity> paymentsPage = new PageImpl<>(payments, request, payments.size());

        when(paymentService.getPaymentPage(pageRequest)).thenReturn(paymentsPage);

        mockMvc.perform(post("/api/v1/ui/payments/page")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pageRequest)))
                .andExpect(status().isOk());

        verify(paymentService).getPaymentPage(pageRequest);
        verify(uiPaymentShortMapper, times(2)).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Обновить информацию по платежу")
    void updatePayment() {
        var updateRequest = new UpdatePaymentRequest()
                .setPaymentId(UUID.randomUUID())
                .setPatch(new PaymentFieldsPatch()
                        .setPaymentSum(BigDecimal.valueOf(1000)));
        var payment = new PaymentEntity().setCounterparty(new CounterpartyEntity()
                .setId(UUID.randomUUID())
                .setName("Name"));

        when(paymentService.updatePayment(updateRequest)).thenReturn(payment);

        mockMvc.perform(put("/api/v1/ui/payments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        verify(paymentService).updatePayment(updateRequest);
        verify(uiPaymentMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Обновить информацию по платежу. Ошибка валидвации")
    void updatePayment_exception() {
        var updateRequest = new UpdatePaymentRequest()
                .setPaymentId(UUID.randomUUID());

        mockMvc.perform(put("/api/v1/ui/payments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(paymentService, never()).updatePayment(any());
        verify(uiPaymentMapper, never()).apply(any());
    }
}

