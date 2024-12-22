package com.profcut.ordermanager.controllers.rest.auth;

import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.domain.dto.auth.RolesResponse;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.security.service.RoleService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WithMockUser(username = "user")
@WebMvcTest(OmRoleApi.class)
@Import(ErrorHttpResponseFactory.class)
public class OmRoleApiTest {

    @MockBean
    JwtUserService jwtUserService;
    @MockBean
    RoleService roleService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    @DisplayName("Успешное получение списка доступных ролей")
    void getAvailableRoles() {
        var roles = Arrays.stream(OmRole.values())
                .map(OmRole::name)
                .collect(Collectors.toSet());
        var response = RolesResponse.builder()
                .availableRoles(roles)
                .build();

        when(roleService.findAll()).thenReturn(roles);

        mockMvc.perform(get("/api/v1/ui/roles"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(roleService).findAll();
    }
}
