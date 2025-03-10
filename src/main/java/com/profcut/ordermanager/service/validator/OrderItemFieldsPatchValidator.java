package com.profcut.ordermanager.service.validator;

import com.profcut.ordermanager.domain.dto.order.OrderItemFieldsPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemFieldsPatchValidator extends BaseValidator<OrderItemFieldsPatch> {

    private final List<OrderItemValidationRule> rules;

    public OrderItemFieldsPatch validate(OrderItemFieldsPatch orderItemFieldsPatch) {
        return validate(orderItemFieldsPatch, rules);
    }
}
