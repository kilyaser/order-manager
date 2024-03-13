package com.profcut.ordermanager.controllers.rest.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.controllers.rest.dto.auth.OmUser;
import com.profcut.ordermanager.controllers.rest.dto.auth.RegisterRequest;
import com.profcut.ordermanager.security.service.AuthenticationService;
import com.profcut.ordermanager.security.service.JwtUserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@Import(ErrorHttpResponseFactory.class)
@WithMockUser(username = "user")
public class AuthenticationControllerTest {

    @MockBean
    AuthenticationService authService;
    @MockBean
    JwtUserService jwtUserService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void register_success() {
        var id = UUID.randomUUID();
        var request = getDefaultRegisterRequest();
        var response = getDefaultOmUser(request, id);

        when(authService.register(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/register", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().json(objectMapper.writeValueAsString(response))
                );

        verify(authService).register(any());
    }

    @Test
    @SneakyThrows
    void register_validate_pass_exception() {
        var id = UUID.randomUUID();
        var request = getDefaultRegisterRequest();
        request.setPassword("wrong_password");
        var response = getDefaultOmUser(request, id);

        when(authService.register(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/register", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());

        verify(authService, never()).register(any());
    }

    private RegisterRequest getDefaultRegisterRequest() {
        return RegisterRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .email("test@mail.ru")
                .password("Password1")
                .build();
    }
    private OmUser getDefaultOmUser(RegisterRequest request, UUID id) {
        return OmUser.builder()
                .id(id)
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .email(request.getEmail())
                .build();
    }
}
