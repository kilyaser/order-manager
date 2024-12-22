package com.profcut.ordermanager.controllers.rest.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.domain.dto.constraint.AdminConstraint;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.service.impl.AdminConstraintService;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user")
@WebMvcTest(AdminConstraintsApi.class)
@Import(ErrorHttpResponseFactory.class)
public class AdminConstraintsApiTest {

    @MockBean
    JwtUserService jwtUserService;
    @MockBean
    AdminConstraintService adminConstraintService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void getConstraints_success(boolean isAllow) {
        var constraints = AdminConstraint.builder()
                .isAllowViewAdminConsole(isAllow)
                .isAllowViewRoles(isAllow)
                .build();

        when(adminConstraintService.getAdminConstraints()).thenReturn(constraints);

        mockMvc.perform(get("/api/v1/ui/admin/constraints"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(constraints)));

        verify(adminConstraintService).getAdminConstraints();
    }
}
