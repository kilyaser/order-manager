package com.profcut.ordermanager.controllers.rest.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.controllers.rest.mapper.UiOrderMapper;
import com.profcut.ordermanager.controllers.rest.mapper.UiOrderShortMapper;
import com.profcut.ordermanager.domain.dto.order.OrderFieldsPatch;
import com.profcut.ordermanager.domain.dto.order.UiOrder;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderRequest;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.enums.OrderState;
import com.profcut.ordermanager.domain.exceptions.OrderNotFoundException;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
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

import java.util.List;
import java.util.UUID;

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.buildDefaultOrder;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultCreateOrderRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderApi.class)
@Import(ErrorHttpResponseFactory.class)
@WithMockUser(username = "user")
public class OrderApiTest {

    @MockBean
    OrderService orderService;
    @MockBean
    UiOrderMapper uiOrderMapper;
    @MockBean
    UiOrderShortMapper uiOrderShortMapper;
    @MockBean
    JwtUserService jwtUserService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    @DisplayName("Успешное создание заказа.")
    void createOrder_success() {
        var request = getDefaultCreateOrderRequest();
        var order = buildDefaultOrder();

        when(orderService.createOrder(request)).thenReturn(order);
        when(uiOrderMapper.apply(order)).thenReturn(new UiOrder());

        mockMvc.perform(post("/api/v1/ui/orders", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(orderService).createOrder(request);
        verify(uiOrderMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Ошибка создания заказа.")
    void createOrder_validate_exception() {
        var request = getDefaultCreateOrderRequest();
        request.setCounterpartyId(null);

        mockMvc.perform(post("/api/v1/ui/orders", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(orderService, never()).createOrder(request);
        verify(uiOrderMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение страницы заказа.")
    void getOrdersPage() {
        var request = new com.profcut.ordermanager.domain.dto.filter.PageRequest()
                .setPage(0).setSize(20);
        var pageRequest = PageConverter.covertToPageable(request);
        var orders = List.of(buildDefaultOrder());
        Page<OrderEntity> pageOrders = new PageImpl<>(orders, pageRequest, orders.size());

        when(orderService.getOrdersPage(request)).thenReturn(pageOrders);

        mockMvc.perform(post("/api/v1/ui/orders/page", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        verify(orderService).getOrdersPage(request);
        verify(uiOrderShortMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение страницы заказа по контрагенту")
    void getOrdersPageByCounterpartyId() {
        var counterpartyId = UUID.randomUUID();
        var request = new com.profcut.ordermanager.domain.dto.filter.PageRequest()
                .setPage(0).setSize(20);
        var pageRequest = PageConverter.covertToPageable(request);
        var orders = List.of(buildDefaultOrder());
        Page<OrderEntity> pageOrders = new PageImpl<>(orders, pageRequest, orders.size());

        when(orderService.findAllOrdersByCounterpartyId(counterpartyId, request)).thenReturn(pageOrders);

        mockMvc.perform(post("/api/v1/ui/orders/{counterpartyId}", counterpartyId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        verify(orderService).findAllOrdersByCounterpartyId(counterpartyId, request);
        verify(uiOrderMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Обновить информацию о зказае. Успешное обновление")
    void updateOrder_success() {
        var request = new UpdateOrderRequest().setId(UUID.randomUUID())
                .setPatch(new OrderFieldsPatch().setBillNumber("111"));
        var order = TestDataHelper.buildDefaultOrder();

        when(orderService.updateOrder(request)).thenReturn(order);

        mockMvc.perform(put("/api/v1/ui/orders", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        verify(orderService).updateOrder(request);
        verify(uiOrderMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Обновить информацию о зказае. Ошибка валидации входящих данных")
    void updateOrder_exception() {
        var request = new UpdateOrderRequest().setId(UUID.randomUUID());

        mockMvc.perform(put("/api/v1/ui/orders", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(orderService, never()).updateOrder(any());
        verify(uiOrderMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Обнолвение статуса заказа")
    void changeState_success() {
        var orderId = UUID.randomUUID();
        var state = OrderState.READY;
        var order = TestDataHelper.buildDefaultOrder();

        when(orderService.changeState(orderId, state)).thenReturn(order);

        mockMvc.perform(put("/api/v1/ui/orders/{orderId}", orderId)
                        .with(csrf())
                        .param("state", state.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService).changeState(orderId, state);
        verify(uiOrderMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение заказа по id")
    void getOrderById() {
        var order = buildDefaultOrder();

        when(orderService.findOrderById(order.getOrderId())).thenReturn(order);

        mockMvc.perform(get("/api/v1/ui/orders/{orderId}", order.getOrderId()))
                .andExpect(status().is2xxSuccessful());

        verify(orderService).findOrderById(order.getOrderId());
        verify(uiOrderMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение заказа по id. Заказ не найден")
    void getOrderById_exception() {
        when(orderService.findOrderById(any())).thenThrow(OrderNotFoundException.class);

        mockMvc.perform(get("/api/v1/ui/orders/{orderId}", UUID.randomUUID()))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()),
                        jsonPath("$.exClass").value("OrderNotFoundException")
                );

        verify(orderService).findOrderById(any(UUID.class));
        verify(uiOrderMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Удаление заказа")
    void deleteOrder() {
        var id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/ui/orders/{orderId}", id)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(orderService).deleteOrderById(id);
    }
}
