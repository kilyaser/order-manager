package com.profcut.ordermanager.service.validator;

import com.profcut.ordermanager.domain.dto.material.UiEstablishMachineRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EstablishMachineValidator extends BaseValidator<UiEstablishMachineRequest> {

    private final List<EstablishMachineValidationRule> rules;

    public UiEstablishMachineRequest validate(UiEstablishMachineRequest request) {
        return validate(request, rules);
    }
}
