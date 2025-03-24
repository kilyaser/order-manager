package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.domain.enums.OrderState;
import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import com.profcut.ordermanager.service.ConstraintService;
import com.profcut.ordermanager.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.profcut.ordermanager.domain.enums.OrderState.CANCELLED;
import static com.profcut.ordermanager.domain.enums.OrderState.COMPLETED;
import static com.profcut.ordermanager.domain.enums.OrderState.SHIPPED;


@Slf4j
@Service("orderConstraintService")
public class OrderConstraintService extends ConstraintService {

    private final OrderService orderService;

    public OrderConstraintService(CurrentUserSecurityService currentUserSecurityService,
                                  OrderService orderService) {
        super(currentUserSecurityService);
        this.orderService = orderService;
    }

    public boolean cadDeleteOrder(UUID orderId) {
        var author = orderService.findOrderById(orderId).getAuthor();
        return hasAllowedRole() || author.equalsIgnoreCase(currentUserSecurityService.getLogin());
    }

    public List<OrderState> getAvailableOrderStates(UUID orderId) {
        var order = orderService.findOrderById(orderId);
        return switch (order.getOrderState()) {
            case NEW, IN_WORK -> List.of(CANCELLED);
            case READY -> List.of(SHIPPED, COMPLETED, CANCELLED);
            case SHIPPED -> List.of(COMPLETED);
            default -> Collections.emptyList();
        };
    }
}
