package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.rest.handlers.EstablishMachineHandler;
import com.profcut.ordermanager.controllers.rest.mapper.UiOrderItemMapper;
import com.profcut.ordermanager.domain.dto.material.UiEstablishMachineRequest;
import com.profcut.ordermanager.domain.dto.order.AddOrderItemsRequest;
import com.profcut.ordermanager.domain.dto.order.DeleteOrderItemRequest;
import com.profcut.ordermanager.domain.dto.order.UiOrderItem;
import com.profcut.ordermanager.domain.dto.order.UiOrderItems;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderItemRequest;
import com.profcut.ordermanager.controllers.rest.handlers.AddOrderItemsHandler;
import com.profcut.ordermanager.controllers.rest.handlers.DeleteOrderItemHandler;
import com.profcut.ordermanager.controllers.rest.handlers.UpdateOrderItemHandler;
import com.profcut.ordermanager.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/items")
@Tag(name = "order-item-api", description = "Контроллер для управления позициями заказами")
public class OrderItemApi {

    private final UpdateOrderItemHandler updateOrderItemHandler;
    private final DeleteOrderItemHandler deleteOrderItemHandler;
    private final AddOrderItemsHandler addOrderItemsHandler;
    private final EstablishMachineHandler establishMachineHandler;
    private final UiOrderItemMapper uiOrderItemMapper;
    private final OrderItemService orderItemService;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновить позиции заказа")
    public UiOrderItems updateOrderItems(@Valid @RequestBody UpdateOrderItemRequest request) {
        return updateOrderItemHandler.handle(request);
    }

    @GetMapping("/{itemId}")
    public UiOrderItem findById(@PathVariable("itemId") UUID itemId) {
        return uiOrderItemMapper.apply(orderItemService.findById(itemId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Добавить позиции в зказа")
    public UiOrderItems addOrderItems(@Valid @RequestBody AddOrderItemsRequest request) {
        return addOrderItemsHandler.handle(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить позицию заказа")
    public void deleteOrderItems(@Valid @RequestBody DeleteOrderItemRequest request) {
        deleteOrderItemHandler.handle(request);
    }

    @PutMapping("/machine/establish")
    public UiOrderItem establishMachines(@Valid @RequestBody UiEstablishMachineRequest request) {
        return establishMachineHandler.handle(request);
    }
}
