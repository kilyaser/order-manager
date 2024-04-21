package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.rest.mapper.UiPaymentMapper;
import com.profcut.ordermanager.controllers.rest.mapper.UiPaymentShortMapper;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.payment.CreatePaymentRequest;
import com.profcut.ordermanager.domain.dto.payment.UiPayment;
import com.profcut.ordermanager.domain.dto.payment.UiPaymentShort;
import com.profcut.ordermanager.domain.dto.payment.UpdatePaymentRequest;
import com.profcut.ordermanager.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/payments")
@Tag(name = "payment-ui-api", description = "Контроллер для управления позициями заказами")
public class PaymentController {

    private final PaymentService paymentService;
    private final UiPaymentMapper uiPaymentMapper;
    private final UiPaymentShortMapper uiPaymentShortMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Добавить платеж по заказу")
    public UiPayment addPayment(@Valid @RequestBody CreatePaymentRequest request) {
        return uiPaymentMapper.apply(paymentService.addPayment(request));
    }

    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить платеж")
    public void deletePayment(@PathVariable UUID paymentId) {
        paymentService.deletePayment(paymentId);
    }

    @GetMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Получить платеж по id")
    public UiPayment getPayment(@PathVariable UUID paymentId) {
        return uiPaymentMapper.apply(paymentService.findPaymentById(paymentId));
    }

    @GetMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Полчить все платежи по заказу")
    public List<UiPaymentShort> getAllPaymentByOrder(@PathVariable UUID orderId) {
        return paymentService.findAllPaymentByOrderId(orderId).stream()
                .map(uiPaymentShortMapper)
                .toList();
    }

    @PostMapping("/counterparty/{counterpartyId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Получить все платежи по контрагенту")
    public Page<UiPaymentShort> findAllPaymentByCounterparty(@PathVariable UUID counterpartyId,
                                                             @RequestBody PageRequest pageRequest) {
        return paymentService.findAllPaymentByCounterpartyId(counterpartyId, pageRequest)
                .map(uiPaymentShortMapper);
    }

    @PostMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Получить страницу платежей")
    public Page<UiPaymentShort> getPaymentPage(@RequestBody PageRequest pageRequest) {
        return paymentService.getPaymentPage(pageRequest).map(uiPaymentShortMapper);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновить информацию по платежу")
    public UiPayment updatePayment(@Valid @RequestBody UpdatePaymentRequest request) {
        return uiPaymentMapper.apply(paymentService.updatePayment(request));
    }
}
