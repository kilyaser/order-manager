package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.order.OrderItemFieldsPatch;
import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrderItemService {

    OrderItemEntity findById(UUID itemId);

    List<OrderItemEntity> createOrderItems(List<OrderItemRequest> itemRequests);

    OrderItemEntity saveItem(OrderItemEntity item);

    List<OrderItemEntity> saveAll(List<OrderItemEntity> items);

    void updateOrderItem(OrderItemFieldsPatch patch);

    void deleteOrderItems(Set<UUID> itemIds);

    void deleteById(UUID itemId);
}
