package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.domain.dto.order.AddOrderItemsRequest;
import com.profcut.ordermanager.domain.dto.order.DeleteOrderItemRequest;
import com.profcut.ordermanager.domain.dto.order.UiOrderItems;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderItemRequest;
import com.profcut.ordermanager.service.handlers.AddOrderItemsHandler;
import com.profcut.ordermanager.service.handlers.DeleteOrderItemHandler;
import com.profcut.ordermanager.service.handlers.UpdateOrderItemHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/items")
@Tag(name = "order-item-api", description = "Контроллер для управления позициями заказами")
public class OrderItemApi {

    private final UpdateOrderItemHandler updateOrderItemHandler;
    private final DeleteOrderItemHandler deleteOrderItemHandler;
    private final AddOrderItemsHandler addOrderItemsHandler;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновить позиции заказа")
    public UiOrderItems updateOrderItems(@Valid @RequestBody UpdateOrderItemRequest request) {
        return updateOrderItemHandler.handle(request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Добавить позиции в зказа")
    public UiOrderItems addOrderItems(@Valid @RequestBody AddOrderItemsRequest request) {
        return addOrderItemsHandler.handle(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Удалить позицию заказа")
    public void deleteOrderItems(@Valid @RequestBody DeleteOrderItemRequest request) {
        deleteOrderItemHandler.handle(request);
    }
}
