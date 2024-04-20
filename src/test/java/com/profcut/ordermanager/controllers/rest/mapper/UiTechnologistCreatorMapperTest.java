package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.technologist.CreateTechnologistRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UiTechnologistCreatorMapperTest {

    UiTechnologistCreatorMapper mapper = Mappers.getMapper(UiTechnologistCreatorMapper.class);

    @Test
    void shouldMapAllFields() {
        var request = CreateTechnologistRequest.builder()
                .firstName("Name")
                .lastName("lastName")
                .firstName("firstName")
                .email("email")
                .phone("111")
                .build();

        var technologist = mapper.apply(request);

        assertNotNull(technologist);
        assertEquals(request.getPhone(), technologist.getPhone());
        assertEquals(request.getFirstName(), technologist.getFirstName());
        assertEquals(request.getLastName(), technologist.getLastName());
        assertEquals(request.getPatronymic(), technologist.getPatronymic());
        assertEquals(request.getEmail(), technologist.getEmail());
    }
}
