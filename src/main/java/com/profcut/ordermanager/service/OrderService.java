package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.order.CreateOrderRequest;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderRequest;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.enums.OrderState;
import org.springframework.data.domain.Page;

import java.util.UUID;


public interface OrderService {

    OrderEntity createOrder(CreateOrderRequest request);

    Page<OrderEntity> getOrdersPage(PageRequest request);

    OrderEntity updateOrder(UpdateOrderRequest request);

    OrderEntity saveOrder(OrderEntity order);

    OrderEntity findOrderById(UUID orderId);

    Page<OrderEntity> findAllOrdersByCounterpartyId(UUID counterpartyId, PageRequest request);

    OrderEntity changeState(UUID orderId, OrderState state);

    void deleteOrderById(UUID orderId);
}
