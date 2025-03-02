package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.rest.mapper.UiContractMapper;
import com.profcut.ordermanager.domain.dto.contract.CreateContractRequest;
import com.profcut.ordermanager.domain.dto.contract.UiContract;
import com.profcut.ordermanager.domain.dto.contract.UiContracts;
import com.profcut.ordermanager.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/contracts")
@Tag(name = "contract-ui-api", description = "Контроллер для управления договорами")
public class ContractApi {

    private final ContractService contractService;
    private final UiContractMapper uiContractMapper;

    @GetMapping("/{contractId}")
    @Operation(description = "Поиск договора по ID")
    public UiContract getContract(@PathVariable("contractId") UUID contractId) {
        return uiContractMapper.apply(contractService.findById(contractId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать договор")
    public UiContract createContract(@Valid @RequestBody CreateContractRequest request) {
        return uiContractMapper.apply(contractService.createContract(request));
    }

    @GetMapping("/counterparty/{counterpartyId}")
    @Operation(description = "Получить договоры по контрагенты")
    public UiContracts getByCounterparty(@PathVariable("counterpartyId") UUID counterpartyId) {
        return contractService.findByAllCounterpartyId(counterpartyId).stream()
                .map(uiContractMapper)
                .collect(Collectors.collectingAndThen(Collectors.toList(), UiContracts::new));
    }
}
