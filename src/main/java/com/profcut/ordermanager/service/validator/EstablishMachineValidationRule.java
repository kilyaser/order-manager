package com.profcut.ordermanager.service.validator;

import com.profcut.ordermanager.domain.dto.material.UiEstablishMachineRequest;
import com.profcut.ordermanager.domain.exceptions.EstablishMachineException;
import com.profcut.ordermanager.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstablishMachineValidationRule implements ValidationRule<UiEstablishMachineRequest> {

   private final OrderItemService orderItemService;

    @Override
    public ValidationResult validate(UiEstablishMachineRequest request) {
        var item =  orderItemService.findById(request.getOrderItemId());
        return item.getQuantity().equals(0) && !request.getMachineIds().isEmpty()
                ? ValidationResult.invalid(new EstablishMachineException(item.getId()))
                : ValidationResult.valid();
    }
}
