package com.profcut.ordermanager.massaging.events;

import com.profcut.ordermanager.domain.entities.OrderItemEntity;

public record OrderItemChangeEvent(OrderItemEntity item) {
}
