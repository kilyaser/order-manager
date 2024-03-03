package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UiTechnologistMapperTest {

    UiTechnologistMapper mapper = Mappers.getMapper(UiTechnologistMapper.class);

    @Test
    void shouldMapAllFields() {
        var id = UUID.randomUUID();
        var technologist = new TechnologistEntity()
                .setId(id)
                .setFirstName("Name")
                .setLastName("lastName")
                .setFirstName("firstName")
                .setEmail("email")
                .setPhone("111");

        var result = mapper.apply(technologist);

        assertNotNull(result);
        assertEquals(technologist.getId(), result.getId());
        assertEquals(technologist.getEmail(), result.getEmail());
        assertEquals(technologist.getFirstName(), result.getFirstName());
        assertEquals(technologist.getLastName(), result.getLastName());
        assertEquals(technologist.getPatronymic(), result.getPatronymic());
        assertEquals(technologist.getPhone(), result.getPhone());
    }
}
