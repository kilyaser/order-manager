package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.ConfigurationTestBeans;
import com.profcut.ordermanager.domain.dto.technologist.TechnologistFieldsPatch;
import com.profcut.ordermanager.domain.dto.technologist.UpdateTechnologistRequest;
import com.profcut.ordermanager.controllers.rest.mapper.UiTechnologistCreatorMapper;
import com.profcut.ordermanager.domain.exceptions.TechnologistNotFoundException;
import com.profcut.ordermanager.domain.repository.TechnologistRepository;
import com.profcut.ordermanager.service.TechnologistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultCreateTechnologistRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(value = "/sql/technologist-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(classes = ConfigurationTestBeans.class)
public class TechnologistServiceTest {

    @Autowired
    TechnologistRepository repository;
    @Autowired
    UiTechnologistCreatorMapper mapper;

    @Autowired
    TechnologistService technologistService;


    @Test
    @DisplayName("Успешное обновление данных технолога.")
    void updateTechnologist() {
        var id = UUID.fromString("423c5ce2-60e8-48a5-9b48-b5f89ca20c07");
        var patch = TechnologistFieldsPatch.builder()
                .email("newEmail@mail.ru")
                .phone("+722222").build();
        var updateRequest = new UpdateTechnologistRequest().setId(id).setPatch(patch);
        var technologist = technologistService.updateTechnologist(updateRequest);

        assertEquals(technologist.getPhone(), patch.getPhone());
        assertEquals(technologist.getEmail(), patch.getEmail());
        assertNotNull(technologist.getFirstName());
        assertNotNull(technologist.getLastName());
        assertNotNull(technologist.getPatronymic());
    }

    @Test
    @DisplayName("Успешное создание технолога")
    void  createTechnologist() {
        var request = getDefaultCreateTechnologistRequest();
        var entity = technologistService.createTechnologist(request);
        var result = repository.findByTechnologistId(entity.getId()).orElseThrow(null);

        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(request.getEmail(), entity.getEmail());
        assertEquals(request.getPhone(), entity.getPhone());
    }

    @Test
    @DisplayName("Посик по id.")
    void getById() {
        var id = UUID.fromString("423c5ce2-60e8-48a5-9b48-b5f89ca20c07");
        var result = technologistService.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("Ошибка поиска по id.")
    void getById_exception() {
        var id = UUID.randomUUID();

        assertThrows(TechnologistNotFoundException.class, () -> technologistService.getById(id));
    }

    @Test
    void deleteById() {
        var id = UUID.fromString("423c5ce2-60e8-48a5-9b48-b5f89ca20c07");
        var isExist = repository.existsById(id);

        assertTrue(isExist);

        technologistService.deleteById(id);

        assertFalse(repository.existsById(id));
    }
}
