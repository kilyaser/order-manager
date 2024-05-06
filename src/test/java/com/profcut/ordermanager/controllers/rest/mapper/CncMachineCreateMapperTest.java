package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.machine.CreateMachineRequest;
import com.profcut.ordermanager.domain.enums.MachineType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CncMachineCreateMapperTest {

    CncMachineCreateMapper machineCreateMapper = Mappers.getMapper(CncMachineCreateMapper.class);

    @Test
    void shouldMapAllFields() {
        var request = new CreateMachineRequest().setMachineType(MachineType.THREE_AXIS)
                .setName("machineName");

        var result = machineCreateMapper.apply(request);

        assertNotNull(result);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(request);
    }
}
