package com.profcut.ordermanager.controllers.rest.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.UiTechnologist;
import com.profcut.ordermanager.controllers.rest.ui.mapper.UiTechnologistMapper;
import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import com.profcut.ordermanager.domain.exceptions.TechnologistNotFoundException;
import com.profcut.ordermanager.servcie.TechnologistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TechnologistController.class)
@Import(ErrorHttpResponseFactory.class)
public class TechnologistControllerTest {

    @MockBean
    TechnologistService technologistService;
    @MockBean
    UiTechnologistMapper uiTechnologistMapper;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_404_not_found_when_get_technologist() throws Exception {
        var id = UUID.randomUUID();
        when(technologistService.getById(id)).thenThrow(TechnologistNotFoundException.class);

        mockMvc.perform(get("/ui/technologists/{technologistId}", id))
                .andExpect(status().isNotFound());

        verify(uiTechnologistMapper, never()).apply(any());
    }

    @Test
    void should_return_ok_when_get_technologist() throws Exception {
        var id = UUID.randomUUID();
        var dto = new UiTechnologist().setId(id);

        when(technologistService.getById(id)).thenReturn(new TechnologistEntity().setId(id));
        when(uiTechnologistMapper.apply(any())).thenReturn(dto);

        mockMvc.perform(get("/ui/technologists/{technologistId}", id))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.id").value(id.toString())
                );

        verify(technologistService).getById(any());
        verify(uiTechnologistMapper).apply(any());
    }

    @Test
    void should_return_404_not_found_when_get_technologist_by_name() throws Exception {
        var fullName = "ФИО";
        when(technologistService.findByName(fullName)).thenThrow(TechnologistNotFoundException.class);

        mockMvc.perform(get("/ui/technologists/name/{fullName}", fullName))
                .andExpect(status().isNotFound());

        verify(uiTechnologistMapper, never()).apply(any());
    }

    @Test
    void should_return_ok_when_get_technologist_by_name() throws Exception {
        var id = UUID.randomUUID();
        var fullName = "ФИО";
        var dto = new UiTechnologist().setId(id).setFullName(fullName);

        when(technologistService.findByName(fullName)).thenReturn(new TechnologistEntity()
                .setId(id).setFullName(fullName));
        when(uiTechnologistMapper.apply(any())).thenReturn(dto);

        mockMvc.perform(get("/ui/technologists/name/{fullName}", fullName))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.id").value(id.toString()),
                        jsonPath("$.fullName").value(fullName)
                );

        verify(technologistService).findByName(any());
        verify(uiTechnologistMapper).apply(any());
    }

    @Test
    void should_return_created_created_technologist() throws Exception {
        var createRequest = CreateTechnologistRequest.builder()
                .fullName("Ф.И.О.")
                .email("email")
                .phone("phone")
                .build();
        var id = UUID.randomUUID();
        var entity = new TechnologistEntity().setId(id)
                .setFullName(createRequest.getFullName())
                .setEmail(createRequest.getEmail())
                .setPhone(createRequest.getPhone());
        var dto = new UiTechnologist()
                .setId(id)
                .setFullName(createRequest.getFullName())
                .setPhone(createRequest.getFullName())
                .setEmail(createRequest.getEmail())
                .setPhone(createRequest.getPhone());

        when(technologistService.createTechnologist(any())).thenReturn(entity);
        when(uiTechnologistMapper.apply(any())).thenReturn(dto);

        mockMvc.perform(post("/ui/technologists", createRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.id").value(id.toString()),
                        jsonPath("$.fullName").value(entity.getFullName()),
                        jsonPath("$.email").value(entity.getEmail()),
                        jsonPath("$.phone").value(entity.getPhone())
                );

        verify(technologistService).createTechnologist(any());
        verify(uiTechnologistMapper).apply(any());
    }
}
