package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.rest.mapper.UiMachineMapper;
import com.profcut.ordermanager.domain.dto.machine.CreateMachineRequest;
import com.profcut.ordermanager.domain.dto.machine.UiMachine;
import com.profcut.ordermanager.domain.dto.machine.UiMachines;
import com.profcut.ordermanager.domain.dto.machine.UpdateMachineRequest;
import com.profcut.ordermanager.service.CncMachineService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/machines")
public class CncMachineApi {

    private final UiMachineMapper uiMachineMapper;
    private final CncMachineService cncMachineService;

    @GetMapping("/{machineId}")
    @Operation(description = "Поиск станка по id")
    public UiMachine getMachine(@PathVariable UUID machineId) {
        return uiMachineMapper.apply(cncMachineService.findById(machineId));
    }

    @GetMapping
    @Operation(description = "Получение списка оборудования")
    public UiMachines getAllMachines() {
        return cncMachineService.findAll().stream()
                .map(uiMachineMapper)
                .collect(Collectors.collectingAndThen(toList(), UiMachines::new));
    }

    @GetMapping("/order-item/{itemId}")
    @Operation(description = "Полчить список занятных станков по id позиции заказа")
    public UiMachines getByOrderItemId(@PathVariable UUID itemId) {
        return cncMachineService.findOccupiedByOrderItemId(itemId).stream()
                .map(uiMachineMapper)
                .collect(Collectors.collectingAndThen(toList(), UiMachines::new));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать станок")
    public UiMachine createMachine(@Valid @RequestBody CreateMachineRequest createRequest) {
        return uiMachineMapper.apply(cncMachineService.createMachine(createRequest));
    }

    @PutMapping
    @Operation(description = "Обновить информацию по станку")
    public UiMachine updateMachine(@Valid @RequestBody UpdateMachineRequest updateRequest) {
        return uiMachineMapper.apply(cncMachineService.updateMachine(updateRequest));
    }

    @DeleteMapping("/{machineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить станок по id")
    public void deleteMachine(@PathVariable UUID machineId) {
        cncMachineService.deleteMachineById(machineId);
    }
}
