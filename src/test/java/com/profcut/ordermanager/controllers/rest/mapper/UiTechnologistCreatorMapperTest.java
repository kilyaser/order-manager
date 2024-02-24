package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.controllers.rest.dto.technologist.CreateTechnologistRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UiTechnologistCreatorMapperTest {

    UiTechnologistCreatorMapper mapper = Mappers.getMapper(UiTechnologistCreatorMapper.class);

    @Test
    void shouldMapAllFields() {
        var request = CreateTechnologistRequest.builder()
                .fullName("ФИО")
                .email("email")
                .phone("111")
                .build();

        var technologist = mapper.apply(request);

        assertNotNull(technologist);
        assertEquals(request.getPhone(), technologist.getPhone());
        assertEquals(request.getFullName(), technologist.getFullName());
        assertEquals(request.getEmail(), technologist.getEmail());
    }
}
