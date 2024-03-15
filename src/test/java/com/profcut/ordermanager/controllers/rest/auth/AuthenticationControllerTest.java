package com.profcut.ordermanager.controllers.rest.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.domain.dto.auth.AuthRequest;
import com.profcut.ordermanager.domain.dto.auth.AuthResponse;
import com.profcut.ordermanager.security.service.AuthenticationService;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.testData.utils.TestDataHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user")
@Import(ErrorHttpResponseFactory.class)
@WebMvcTest(AuthenticationController.class)
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
        var request = TestDataHelper.getDefaultRegisterRequest();
        var response = TestDataHelper.getDefaultOmUser(request, id);

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
        var request = TestDataHelper.getDefaultRegisterRequest();
        request.setPassword("wrong_password");
        var response = TestDataHelper.getDefaultOmUser(request, id);

        when(authService.register(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/register", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());

        verify(authService, never()).register(any());
    }

    @Test
    @SneakyThrows
    void authenticate_success() {
        var request = AuthRequest.builder().email("test@email.ru").password("Password1234").build();
        var response = AuthResponse.builder().accessToken("accessToken").refreshToken("refreshToken").build();

        when(authService.authenticate(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/authenticate", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().json(objectMapper.writeValueAsString(response))
                );

        verify(authService).authenticate(request);
    }

    @Test
    @SneakyThrows
    void refreshToken_get_new() {
        var response = AuthResponse.builder().accessToken("accessToken").refreshToken("refreshToken").build();

        when(authService.refreshToken(any(Principal.class))).thenReturn(response);
        mockMvc.perform(get("/api/v1/auth/refresh-token"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}
