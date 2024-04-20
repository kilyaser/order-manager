package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.order.CreateOrderRequest;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;


public interface OrderService {

    OrderEntity createOrder(CreateOrderRequest request);

    Page<OrderEntity> getOrdersPage(PageRequest request);

    OrderEntity findOrderById(UUID orderId);

    void deleteOrderById(UUID orderId);
}
