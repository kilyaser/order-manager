package com.profcut.ordermanager.controllers.rest.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.controllers.rest.mapper.UiMachineMapper;
import com.profcut.ordermanager.domain.dto.machine.CreateMachineRequest;
import com.profcut.ordermanager.domain.dto.machine.UiMachine;
import com.profcut.ordermanager.domain.dto.machine.UpdateMachineRequest;
import com.profcut.ordermanager.domain.entities.CncMachineEntity;
import com.profcut.ordermanager.domain.enums.MachineType;
import com.profcut.ordermanager.domain.exceptions.CncMachineNotFoundException;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.service.CncMachineService;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
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

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultMachine;
import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.getDefaultUiMachine;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user")
@Import(ErrorHttpResponseFactory.class)
@WebMvcTest(CncMachineApi.class)
public class CncMachineApiTest {

    @MockBean
    UiMachineMapper uiMachineMapper;
    @MockBean
    CncMachineService cncMachineService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    JwtUserService jwtUserService;

    @Test
    @SneakyThrows
    @DisplayName("Успешное получени информации по станку по id")
    void getCncMachine() {
        var id = UUID.randomUUID();
        var uiMachine = new UiMachine(id, MachineType.THREE_AXIS, "machineName", false, null, null);

        when(cncMachineService.findById(id)).thenReturn(new CncMachineEntity());
        when(uiMachineMapper.apply(any())).thenReturn(uiMachine);

        mockMvc.perform(get("/api/v1/ui/machines/{machineId}", id))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().string(objectMapper.writeValueAsString(uiMachine))
                );

        verify(cncMachineService).findById(id);
        verify(uiMachineMapper).apply(any(CncMachineEntity.class));
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное получени информации по станку по id. CncMachine not found")
    void getCncMachine_exception() {
        var id = UUID.randomUUID();

        when(cncMachineService.findById(id)).thenThrow(CncMachineNotFoundException.class);

        mockMvc.perform(get("/api/v1/ui/machines/{machineId}", id))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()),
                        jsonPath("$.exClass").value("CncMachineNotFoundException"));

        verify(cncMachineService).findById(id);
        verify(uiMachineMapper, never()).apply(any(CncMachineEntity.class));
    }

    @Test
    @SneakyThrows
    @DisplayName("Создание станка")
    void createCncMachine() {
        var machine = getDefaultMachine();
        var request = TestDataHelper.getDefaultCreateMachineRequest();
        var uiMachine = TestDataHelper.getDefaultUiMachine();

        when(cncMachineService.createMachine(request)).thenReturn(machine);
        when(uiMachineMapper.apply(machine)).thenReturn(uiMachine);

        mockMvc.perform(post("/api/v1/ui/machines", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isCreated(),
                        content().string(objectMapper.writeValueAsString(uiMachine))
                );

        verify(cncMachineService).createMachine(request);
        verify(uiMachineMapper).apply(machine);
    }

    @Test
    @SneakyThrows
    @DisplayName("Создание станка. Ошибка валидации")
    void createCncMachine_exception() {
        var request = new CreateMachineRequest();

        mockMvc.perform(post("/api/v1/ui/machines", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(cncMachineService, never()).createMachine(any());
        verify(uiMachineMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Обновить информацию о станке")
    void updateMachine() {
        var updateRequest = new UpdateMachineRequest()
                .setMachineId(UUID.randomUUID())
                .setMachineType(MachineType.THREE_AXIS)
                .setName("machineName");
        var machine = getDefaultMachine();
        var uiMachine = getDefaultUiMachine();

        when(cncMachineService.updateMachine(updateRequest)).thenReturn(machine);
        when(uiMachineMapper.apply(machine)).thenReturn(uiMachine);

        mockMvc.perform(put("/api/v1/ui/machines", updateRequest)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(uiMachine))
                );

        verify(cncMachineService).updateMachine(updateRequest);
        verify(uiMachineMapper).apply(machine);
    }

    @Test
    @SneakyThrows
    @DisplayName("Обновить информацию о станке. Ошибка валидации.")
    void updateMachine_exception() {
        var updateRequest = new UpdateMachineRequest()
                .setMachineType(MachineType.THREE_AXIS)
                .setName("machineName");

        mockMvc.perform(put("/api/v1/ui/machines", updateRequest)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(cncMachineService, never()).updateMachine(any());
        verify(uiMachineMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Удаление станка по id")
    void deleteMachine() {
        var id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/ui/machines/{machineId}", id)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(cncMachineService).deleteMachineById(id);
    }
}
