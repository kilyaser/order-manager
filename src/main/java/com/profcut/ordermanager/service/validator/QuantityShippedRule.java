package com.profcut.ordermanager.service.validator;

import com.profcut.ordermanager.domain.dto.order.OrderItemFieldsPatch;
import com.profcut.ordermanager.domain.exceptions.QuantityShippedException;
import com.profcut.ordermanager.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuantityShippedRule implements OrderItemValidationRule {

    private final OrderItemService orderItemService;

    @Override
    public ValidationResult validate(OrderItemFieldsPatch value) {
        var quantity = getQuantity(value);
        return Optional.ofNullable(value.getQuantityShipped())
                .map(shipped -> checkQuantity(quantity, shipped))
                .orElseGet(ValidationResult::valid);
    }

    private ValidationResult checkQuantity(Integer quantity, Integer quantityShipped) {
        if (quantity >= quantityShipped) {
            return ValidationResult.valid();
        }
        return ValidationResult.invalid(new QuantityShippedException(quantityShipped - quantity));
    }

    private Integer getQuantity(OrderItemFieldsPatch value) {
        return value.getQuantity() != null
                ? value.getQuantity()
                : orderItemService.findById(value.getItemId()).getQuantity();
    }
}
