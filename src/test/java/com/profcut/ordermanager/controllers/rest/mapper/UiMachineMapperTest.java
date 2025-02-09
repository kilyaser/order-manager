package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.entities.CncMachineEntity;
import com.profcut.ordermanager.domain.enums.MachineType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UiMachineMapperTest {

    @Mock
    UiOrderShortMapper orderShortMapper;
    @Mock
    UiOrderItemMapper orderItemMapper;
    @InjectMocks
    UiMachineMapperImpl machineMapper;

    @Test
    void shouldMapAllFields() {
        var machine = new CncMachineEntity().setId(UUID.randomUUID())
                .setMachineType(MachineType.THREE_AXIS)
                .setName("machineName");

        var result = machineMapper.apply(machine);

        assertNotNull(result);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("order", "orderItem")
                .isEqualTo(machine);
    }
}
