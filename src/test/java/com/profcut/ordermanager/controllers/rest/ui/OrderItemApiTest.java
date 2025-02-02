package com.profcut.ordermanager.controllers.rest.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.domain.dto.order.AddOrderItemsRequest;
import com.profcut.ordermanager.domain.dto.order.DeleteOrderItemRequest;
import com.profcut.ordermanager.domain.dto.order.OrderItemFieldsPatch;
import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderItemRequest;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.service.handlers.AddOrderItemsHandler;
import com.profcut.ordermanager.service.handlers.DeleteOrderItemHandler;
import com.profcut.ordermanager.service.handlers.UpdateOrderItemHandler;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemApi.class)
@Import(ErrorHttpResponseFactory.class)
@WithMockUser(username = "user")
public class OrderItemApiTest {

    @MockBean
    UpdateOrderItemHandler updateOrderItemHandler;
    @MockBean
    DeleteOrderItemHandler deleteOrderItemHandler;
    @MockBean
    AddOrderItemsHandler addOrderItemsHandler;
    @MockBean
    JwtUserService jwtUserService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    @DisplayName("Обновление позиции заказа")
    void updateOrderItems() {
        var request = new UpdateOrderItemRequest()
                .setOrderId(UUID.randomUUID())
                .setPatch(List.of(new OrderItemFieldsPatch()
                        .setItemId(UUID.randomUUID())
                        .setProductId(UUID.randomUUID())
                        .setQuantity(5)));

        mockMvc.perform(put("/api/v1/ui/items", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(updateOrderItemHandler).handle(request);
    }

    @Test
    @SneakyThrows
    @DisplayName("Обновление позиции заказа. Ошибка валидации данных.")
    void updateOrderItems_exception() {
        var request = new UpdateOrderItemRequest()
                .setOrderId(UUID.randomUUID())
                .setPatch(List.of(new OrderItemFieldsPatch()
                        .setProductId(UUID.randomUUID())
                        .setQuantity(5)));

        mockMvc.perform(put("/api/v1/ui/items", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(updateOrderItemHandler, never()).handle(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Добавить позицию в заказ")
    void addOrderItems() {
        var addRequest = new AddOrderItemsRequest().setOrderId(UUID.randomUUID())
                .setItemsRequest(List.of(OrderItemRequest.builder()
                        .productId(UUID.randomUUID())
                        .quantity(2)
                        .pricePerProduct(BigDecimal.valueOf(1000))
                        .build()));

        mockMvc.perform(post("/api/v1/ui/items", addRequest)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    @DisplayName("Добавить позицию в заказ. Ошибка валидации данных")
    void addOrderItems_exception() {
        var addRequest = new AddOrderItemsRequest()
                .setItemsRequest(Collections.emptyList());

        mockMvc.perform(post("/api/v1/ui/items", addRequest)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRequest)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Удалене позиции заказа.")
    void deleteOrderItems() {
        var deleteRequest = new DeleteOrderItemRequest()
                .setOrderId(UUID.randomUUID())
                .setDeleteItemIds(Set.of(UUID.randomUUID()));

        mockMvc.perform(delete("/api/v1/ui/items", deleteRequest)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpect(status().isNoContent());

        verify(deleteOrderItemHandler).handle(deleteRequest);
    }

    @Test
    @SneakyThrows
    @DisplayName("Удалене позиции заказа. Ошибка валидации")
    void deleteOrderItems_exception() {
        var deleteRequest = new DeleteOrderItemRequest()
                .setDeleteItemIds(Set.of(UUID.randomUUID()));

        mockMvc.perform(delete("/api/v1/ui/items", deleteRequest)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(deleteOrderItemHandler, never()).handle(any());
    }
}
