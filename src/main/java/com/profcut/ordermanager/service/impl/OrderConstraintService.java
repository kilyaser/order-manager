package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import com.profcut.ordermanager.service.ConstraintService;
import com.profcut.ordermanager.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;


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
}