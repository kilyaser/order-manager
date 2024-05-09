package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.CncMachineCreateMapper;
import com.profcut.ordermanager.domain.dto.machine.UpdateMachineRequest;
import com.profcut.ordermanager.domain.entities.CncMachineEntity;
import com.profcut.ordermanager.domain.enums.MachineType;
import com.profcut.ordermanager.domain.exceptions.CncMachineNotFoundException;
import com.profcut.ordermanager.domain.repository.CncMachineRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultCreateMachineRequest;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultMachine;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CncMachineServiceTest {

    @Mock
    CncMachineRepository machineRepository;
    @Mock
    CncMachineCreateMapper machineCreateMapper;

    @InjectMocks
    CncMachineServiceImpl cncMachineService;

    @Test
    @DisplayName("Получить станок по id")
    void findById() {
        var id = UUID.randomUUID();

        when(machineRepository.findById(id)).thenReturn(Optional.ofNullable(getDefaultMachine()));

        assertThatCode(() -> cncMachineService.findById(id)).doesNotThrowAnyException();

        verify(machineRepository).findById(id);
    }

    @Test
    @DisplayName("Получить станок по id. Станок не найден")
    void findById_exception() {
        var id = UUID.randomUUID();

        when(machineRepository.findById(id)).thenReturn(Optional.empty());

        assertThatCode(() -> cncMachineService.findById(id)).isInstanceOf(CncMachineNotFoundException.class);

        verify(machineRepository).findById(id);
    }

    @Test
    @DisplayName("Создание станка")
    void createMachine() {
        var createRequest = getDefaultCreateMachineRequest();
        var machine = getDefaultMachine();

        when(machineCreateMapper.apply(createRequest)).thenReturn(machine);
        when(machineRepository.save(machine)).thenReturn(machine);

        assertThatCode(() -> cncMachineService.createMachine(createRequest)).doesNotThrowAnyException();

        verify(machineCreateMapper).apply(createRequest);
        verify(machineRepository).save(machine);
    }

    @Test
    @DisplayName("Обновление станка")
    void updateMachine() {
        var updateRequest = new UpdateMachineRequest(UUID.randomUUID(), MachineType.FIVE_AXIS, "Haas");
        var machine = getDefaultMachine();
        var captor = ArgumentCaptor.forClass(CncMachineEntity.class);

        when(machineRepository.findById(any())).thenReturn(Optional.ofNullable(machine));

        assertThatCode(() -> cncMachineService.updateMachine(updateRequest)).doesNotThrowAnyException();

        verify(machineRepository).findById(any(UUID.class));
        verify(machineRepository).save(captor.capture());

        assertThat(captor.getValue())
                .isInstanceOf(CncMachineEntity.class)
                .satisfies(m -> {
                   assertThat(m.getMachineType()).isEqualTo(updateRequest.getMachineType());
                   assertThat(m.getName()).isEqualTo(updateRequest.getName());
                });
    }

    @Test
    @DisplayName("Удаление станка")
    void deleteMachine() {
        var id = UUID.randomUUID();

        assertThatCode(() -> cncMachineService.deleteMachineById(id)).doesNotThrowAnyException();

        verify(machineRepository).deleteById(id);
    }
}
