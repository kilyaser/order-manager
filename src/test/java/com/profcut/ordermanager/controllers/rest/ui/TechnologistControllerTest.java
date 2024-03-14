package com.profcut.ordermanager.controllers.rest.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.domain.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.domain.dto.technologist.TechnologistFieldsPatch;
import com.profcut.ordermanager.domain.dto.technologist.UiTechnologist;
import com.profcut.ordermanager.domain.dto.technologist.UpdateTechnologistRequest;
import com.profcut.ordermanager.controllers.rest.mapper.UiTechnologistMapper;
import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import com.profcut.ordermanager.domain.exceptions.TechnologistNotFoundException;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.service.TechnologistService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TechnologistController.class)
@Import(ErrorHttpResponseFactory.class)
@WithMockUser(username = "user")
public class TechnologistControllerTest {

    @MockBean
    TechnologistService technologistService;
    @MockBean
    UiTechnologistMapper uiTechnologistMapper;
    @MockBean
    JwtUserService jwtUserService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void should_return_404_not_found_when_get_technologist() {
        var id = UUID.randomUUID();
        when(technologistService.getById(id)).thenThrow(TechnologistNotFoundException.class);

        mockMvc.perform(get("/api/v1/ui/technologists/{technologistId}", id))
                .andExpect(status().isNotFound());

        verify(uiTechnologistMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    void should_return_ok_when_get_technologist() {
        var id = UUID.randomUUID();
        var dto = new UiTechnologist().setId(id);

        when(technologistService.getById(id)).thenReturn(new TechnologistEntity().setId(id));
        when(uiTechnologistMapper.apply(any())).thenReturn(dto);

        mockMvc.perform(get("/api/v1/ui/technologists/{technologistId}", id))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.id").value(id.toString())
                );

        verify(technologistService).getById(any());
        verify(uiTechnologistMapper).apply(any());
    }

    @Test
    @SneakyThrows
    void should_return_created_created_technologist() {
        var createRequest = CreateTechnologistRequest.builder()
                .firstName("Name")
                .lastName("lastName")
                .patronymic("Patronymic")
                .email("email")
                .phone("phone")
                .build();
        var id = UUID.randomUUID();
        var entity = new TechnologistEntity().setId(id)
                .setFirstName(createRequest.getFirstName())
                .setLastName(createRequest.getLastName())
                .setPatronymic(createRequest.getPatronymic())
                .setEmail(createRequest.getEmail())
                .setPhone(createRequest.getPhone());
        var dto = new UiTechnologist()
                .setId(id)
                .setFirstName(createRequest.getFirstName())
                .setLastName(createRequest.getLastName())
                .setPatronymic(createRequest.getPatronymic())
                .setEmail(createRequest.getEmail())
                .setPhone(createRequest.getPhone());

        when(technologistService.createTechnologist(any())).thenReturn(entity);
        when(uiTechnologistMapper.apply(any())).thenReturn(dto);

        mockMvc.perform(post("/api/v1/ui/technologists", createRequest)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.id").value(id.toString()),
                        jsonPath("$.firstName").value(entity.getFirstName()),
                        jsonPath("$.lastName").value(entity.getLastName()),
                        jsonPath("$.patronymic").value(entity.getPatronymic()),
                        jsonPath("$.email").value(entity.getEmail()),
                        jsonPath("$.phone").value(entity.getPhone())
                );

        verify(technologistService).createTechnologist(any());
        verify(uiTechnologistMapper).apply(any());
    }

    @Test
    @SneakyThrows
    void should_update_technologist() {
        var id = UUID.randomUUID();
        var firsName = "firstName";
        var email = "new email";
        var phone = "0123";
        var patch = TechnologistFieldsPatch.builder().firstName(firsName)
                .email(email).phone(phone).build();
        var updateRequest = new UpdateTechnologistRequest().setId(id).setPatch(patch);
        var dto = new UiTechnologist().setId(id).setFirstName(firsName).setPhone(phone).setEmail(email);
        var updatedTech = new TechnologistEntity().setId(id).setFirstName(firsName).setEmail(email).setPhone(phone);

        when(technologistService.updateTechnologist(updateRequest)).thenReturn(updatedTech);
        when(uiTechnologistMapper.apply(any())).thenReturn(dto);

        mockMvc.perform(put("/api/v1/ui/technologists", updateRequest)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateRequest)))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.id").value(id.toString()),
                        jsonPath("$.firstName").value(firsName),
                        jsonPath("$.email").value(email),
                        jsonPath("$.phone").value(phone)
                );

        verify(technologistService).updateTechnologist(any());
        verify(uiTechnologistMapper).apply(any());
    }

    @Test
    @SneakyThrows
    void should_delete_technologist() {
        var id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/ui/technologists/{technologistId}", id)
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());
        verify(technologistService).deleteById(any());
    }
}
