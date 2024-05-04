package com.profcut.ordermanager.controllers.rest.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.controllers.rest.mapper.UiMaterialMapper;
import com.profcut.ordermanager.domain.dto.material.CreateMaterialRequest;
import com.profcut.ordermanager.domain.dto.material.UiMaterial;
import com.profcut.ordermanager.domain.entities.MaterialEntity;
import com.profcut.ordermanager.domain.exceptions.MaterialNotFoundException;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.service.MaterialService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaterialController.class)
@Import(ErrorHttpResponseFactory.class)
@WithMockUser(username = "user")
public class MaterialControllerTest {

    @MockBean
    MaterialService materialService;
    @MockBean
    UiMaterialMapper uiMaterialMapper;
    @MockBean
    JwtUserService jwtUserService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    @DisplayName("Успешное создание материала.")
    void createMaterial_success() {
        var request = CreateMaterialRequest.builder()
                .materialType("Steel")
                .build();
        var material = new MaterialEntity().setId(UUID.randomUUID())
                .setMaterialType(request.getMaterialType());
        var uiMaterial = new UiMaterial(material.getId(), material.getMaterialType());

        when(materialService.createMaterial(request)).thenReturn(material);
        when(uiMaterialMapper.apply(material)).thenReturn(uiMaterial);

        mockMvc.perform(post("/api/v1/ui/materials", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isCreated(),
                        content().string(objectMapper.writeValueAsString(uiMaterial))
                );

        verify(materialService).createMaterial(request);
        verify(uiMaterialMapper).apply(material);
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное создание материала. Ошибка валидации")
    void createMaterial_exception() {
        var request = CreateMaterialRequest.builder()
                .materialType("")
                .build();

        mockMvc.perform(post("/api/v1/ui/materials", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException")
                );

        verify(materialService, never()).createMaterial(any());
        verify(uiMaterialMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение материала по id")
    void getMaterialById() {
        var id = UUID.randomUUID();
        var material = new MaterialEntity().setId(id)
                .setMaterialType("steal");
        var uiMaterial = new UiMaterial(material.getId(), material.getMaterialType());

        when(materialService.findById(id)).thenReturn(material);
        when(uiMaterialMapper.apply(material)).thenReturn(uiMaterial);

        mockMvc.perform(get("/api/v1/ui/materials/{materialId}", id))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().string(objectMapper.writeValueAsString(uiMaterial))
                );

        verify(materialService).findById(id);
        verify(uiMaterialMapper).apply(material);
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение материала по id. Материал не найден")
    void getMaterialById_exception() {
        var id = UUID.randomUUID();

        when(materialService.findById(id)).thenThrow(MaterialNotFoundException.class);

        mockMvc.perform(get("/api/v1/ui/materials/{materialId}", id))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()),
                        jsonPath("$.exClass").value("MaterialNotFoundException")
                );

        verify(materialService).findById(id);
        verify(uiMaterialMapper, never()).apply(any());
    }
}
