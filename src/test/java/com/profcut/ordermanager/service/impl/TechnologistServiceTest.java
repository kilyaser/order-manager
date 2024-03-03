package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.ConfigurationTestBeans;
import com.profcut.ordermanager.controllers.rest.dto.technologist.TechnologistFieldsPatch;
import com.profcut.ordermanager.controllers.rest.dto.technologist.UpdateTechnologistRequest;
import com.profcut.ordermanager.controllers.rest.mapper.UiTechnologistCreatorMapper;
import com.profcut.ordermanager.domain.repository.TechnologistRepository;
import com.profcut.ordermanager.service.TechnologistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql("/sql/technologist-test.sql")
@SpringBootTest(classes = ConfigurationTestBeans.class)
public class TechnologistServiceTest {

    @Autowired
    TechnologistRepository repository;
    @Autowired
    UiTechnologistCreatorMapper mapper;

    @Autowired
    TechnologistService technologistService;


    @Test
    void updateTechnologist() {
        var id = UUID.fromString("423c5ce2-60e8-48a5-9b48-b5f89ca20c07");
        var patch = TechnologistFieldsPatch.builder()
                .email("newEmail@mail.ru")
                .phone("2222").build();
        var updateRequest = new UpdateTechnologistRequest().setId(id).setPatch(patch);
        var technologist = technologistService.updateTechnologist(updateRequest);

        assertEquals(technologist.getPhone(), patch.getPhone());
        assertEquals(technologist.getEmail(), patch.getEmail());
        assertNotNull(technologist.getFirstName());
        assertNotNull(technologist.getLastName());
        assertNotNull(technologist.getPatronymic());
    }
}
