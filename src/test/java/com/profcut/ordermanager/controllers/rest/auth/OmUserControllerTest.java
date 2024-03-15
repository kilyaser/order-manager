package com.profcut.ordermanager.controllers.rest.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.controllers.rest.mapper.OmUserMapper;
import com.profcut.ordermanager.domain.dto.auth.OmUser;
import com.profcut.ordermanager.domain.dto.auth.OmUserFieldPatch;
import com.profcut.ordermanager.domain.dto.auth.UpdateOmUserRequest;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.security.service.OmUserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockUser(username = "user")
@WebMvcTest(OmUserController.class)
@Import(ErrorHttpResponseFactory.class)
public class OmUserControllerTest {

    @MockBean
    JwtUserService jwtUserService;
    @MockBean
    OmUserService omUserService;
    @MockBean
    OmUserMapper omUserMapper;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;


    @Test
    @SneakyThrows
    @DisplayName("Успешное обновление юзера")
    void changeOmUser() {
        var id = UUID.randomUUID();
        var patch = OmUserFieldPatch.builder().lastName("NewName").build();
        var request = new UpdateOmUserRequest().setId(id).setPatch(patch);
        var response = OmUser.builder().id(id).firstName("firstName").lastName(patch.getLastName()).build();

        when(omUserService.updateOmUser(request)).thenReturn(new OmUserEntity().setId(id));
        when(omUserMapper.apply((OmUserEntity) any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/users/change", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.id").value(id.toString()),
                        jsonPath("$.lastName").value(patch.getLastName())
                );

        verify(omUserService).updateOmUser(request);
        verify(omUserMapper).apply(any(OmUserEntity.class));
    }

    @Test
    @SneakyThrows
    @DisplayName("Ошибка обновления юзера: patch = null")
    void changeOmUser_exception() {
        var id = UUID.randomUUID();
        var request = new UpdateOmUserRequest().setId(id).setPatch(null);
        when(omUserService.updateOmUser(request)).thenReturn(new OmUserEntity().setId(id));

        mockMvc.perform(put("/api/v1/users/change", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());

        verify(omUserService, never()).updateOmUser(request);
        verify(omUserMapper, never()).apply(any(OmUserEntity.class));
    }

    @Test
    @SneakyThrows
    @DisplayName("Ошибка обновления юзера: incorrect email")
    void changeOmUser_email_exception() {
        var id = UUID.randomUUID();
        var patch = OmUserFieldPatch.builder().lastName("NewName").email("mail.ru").build();
        var request = new UpdateOmUserRequest().setId(id).setPatch(patch);

        when(omUserService.updateOmUser(request)).thenReturn(new OmUserEntity().setId(id));

        mockMvc.perform(put("/api/v1/users/change", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());

        verify(omUserService, never()).updateOmUser(request);
        verify(omUserMapper, never()).apply(any(OmUserEntity.class));
    }
}
