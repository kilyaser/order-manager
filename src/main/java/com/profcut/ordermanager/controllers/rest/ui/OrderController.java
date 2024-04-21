package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderMapper;
import com.profcut.ordermanager.controllers.rest.mapper.UiOrderShortMapper;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.order.CreateOrderRequest;
import com.profcut.ordermanager.domain.dto.order.UiOrder;
import com.profcut.ordermanager.domain.dto.order.UiOrderShort;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderRequest;
import com.profcut.ordermanager.domain.enums.OrderState;
import com.profcut.ordermanager.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/orders")
@Tag(name = "order-ui-api", description = "Контроллер для управления заказами")
public class OrderController {

    private final OrderService orderService;
    private final UiOrderMapper orderMapper;
    private final UiOrderShortMapper orderShortMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создание заказа.")
    public UiOrder createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderMapper.apply(orderService.createOrder(request));
    }

    @PostMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Получение страницы заказов")
    public Page<UiOrderShort> getOrdersPage(@RequestBody PageRequest request) {
        return orderService.getOrdersPage(request).map(orderShortMapper);
    }

    @PostMapping("/{counterpartyId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Получиение всех заказав по Контрегенту")
    public Page<UiOrderShort> getAllOrdersByCounterparty(@PathVariable UUID counterpartyId,
                                                         @RequestBody PageRequest pageRequest) {
        return orderService.findAllOrdersByCounterpartyId(counterpartyId, pageRequest)
                .map(orderShortMapper);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновить инофрмацию о заказе")
    public UiOrder updateOrder(@Valid @RequestBody UpdateOrderRequest request) {
        return orderMapper.apply(orderService.updateOrder(request));
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Получить заказ по id")
    public UiOrder getOrderById(@PathVariable UUID orderId) {
        return orderMapper.apply(orderService.findOrderById(orderId));
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Изменить позицию заказа")
    public UiOrder changeOrderSate(@PathVariable UUID orderId,
                                   @RequestParam("state") OrderState state) {
        return orderMapper.apply(orderService.changeState(orderId, state));
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удаление заказа по id")
    @PreAuthorize("@orderConstraintService.cadDeleteOrder(#orderId)")
    void deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrderById(orderId);
    }
}
