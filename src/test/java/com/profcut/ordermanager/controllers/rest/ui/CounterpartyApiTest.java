package com.profcut.ordermanager.controllers.rest.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
import com.profcut.ordermanager.controllers.rest.mapper.UiCounterpartyMapper;
import com.profcut.ordermanager.controllers.rest.mapper.UiCounterpartyShortMapper;
import com.profcut.ordermanager.domain.dto.counterparty.CounterpartyFieldsPatch;
import com.profcut.ordermanager.domain.dto.counterparty.CreateCounterpartyRequest;
import com.profcut.ordermanager.domain.dto.counterparty.UiCounterparty;
import com.profcut.ordermanager.domain.dto.counterparty.UpdateCounterpartyRequest;
import com.profcut.ordermanager.domain.dto.filter.FilterRequest;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import com.profcut.ordermanager.security.service.JwtUserService;
import com.profcut.ordermanager.service.CounterpartyService;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import com.profcut.ordermanager.utils.jpa.specification.PageConverter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.stream.Stream;

import static com.profcut.ordermanager.testData.utils.helper.TestDataHelper.buildDefaultCounterparty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user")
@Import(ErrorHttpResponseFactory.class)
@WebMvcTest(CounterpartyApi.class)
public class CounterpartyApiTest {

    @MockBean
    JwtUserService jwtUserService;
    @MockBean
    UiCounterpartyMapper uiCounterpartyMapper;
    @MockBean
    UiCounterpartyShortMapper shortMapper;
    @MockBean
    CounterpartyService counterpartyService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    @DisplayName("Успешно получить контрагента по id")
    void getCounterparty_success() {
        var id = UUID.randomUUID();
        var counterparty = buildDefaultCounterparty(id);
        var uiCounterparty = new UiCounterparty();
        uiCounterparty.setId(id).setInn(counterparty.getInn()).setName(counterparty.getName());

        when(counterpartyService.findById(id)).thenReturn(counterparty);
        when(uiCounterpartyMapper.apply(any())).thenReturn(uiCounterparty);

        mockMvc.perform(get("/api/v1/ui/counterparties/{counterpartyId}", id))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().string(objectMapper.writeValueAsString(uiCounterparty))
                );

        verify(counterpartyService).findById(id);
        verify(uiCounterpartyMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Поиск контрагента по фильтру")
    void findCounterpartyFilter() {
        var filter = FilterRequest.builder()
                .search("name").build();
        var counterparties = Stream.of(UUID.randomUUID(), UUID.randomUUID())
                .map(TestDataHelper::buildDefaultCounterparty).toList();
        var pageRequest = PageRequest.of(0, 20);
        Page<CounterpartyEntity> counterpartyPage = new PageImpl<>(counterparties, pageRequest, counterparties.size());

        when(counterpartyService.findCounterpartiesByFilter(filter)).thenReturn(counterpartyPage);

        mockMvc.perform(post("/api/v1/ui/counterparties/find", filter)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().is2xxSuccessful());

        verify(counterpartyService).findCounterpartiesByFilter(any());
        verify(uiCounterpartyMapper, times(2)).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение страницы заказа")
    void getCounterpartyPage() {
        var request = new com.profcut.ordermanager.domain.dto.filter.PageRequest()
                .setPage(0).setSize(20);
        var counterparties = Stream.of(UUID.randomUUID(), UUID.randomUUID())
                .map(TestDataHelper::buildDefaultCounterparty).toList();
        var pageRequest = PageConverter.covertToPageable(request);
        Page<CounterpartyEntity> counterpartiesPage = new PageImpl<>(counterparties, pageRequest, counterparties.size());

        when(counterpartyService.getCounterpartiesPage(request)).thenReturn(counterpartiesPage);

        mockMvc.perform(post("/api/v1/ui/counterparties/page", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        verify(counterpartyService).getCounterpartiesPage(any());
        verify(uiCounterpartyMapper, times(2)).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное создание контрагента")
    void createCounterparty_success() {
        var request = CreateCounterpartyRequest.builder()
                .name("counterpartyName")
                .email("test@mail.ru")
                .phone("+71234567852")
                .build();
        var savedCounterparty = buildDefaultCounterparty(UUID.randomUUID());
        UiCounterparty response = new UiCounterparty();
        response.setEmail(request.getEmail())
                .setPhone(request.getPhone())
                .setId(UUID.randomUUID())
                .setName(request.getName());

        when(counterpartyService.createCounterparty(request)).thenReturn(savedCounterparty);
        when(uiCounterpartyMapper.apply(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/ui/counterparties", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isCreated(),
                        content().string(objectMapper.writeValueAsString(response))
                );

        verify(counterpartyService).createCounterparty(request);
        verify(uiCounterpartyMapper).apply(savedCounterparty);
    }

    @Test
    @SneakyThrows
    @DisplayName("Ошибка валидации при создании контрагента")
    void createCounterparty_exception() {
        var request = CreateCounterpartyRequest.builder()
                .name("name").phone("").build();

        mockMvc.perform(post("/api/v1/ui/counterparties", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(counterpartyService, never()).createCounterparty(any());
        verify(uiCounterpartyMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное обновление контаргента")
    void updateCounterparty_success() {
        var request = new UpdateCounterpartyRequest().setId(UUID.randomUUID())
                .setPatch(CounterpartyFieldsPatch.builder().name("newName").build());

        mockMvc.perform(put("/api/v1/ui/counterparties", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        verify(counterpartyService).updateCounterparty(any());
        verify(uiCounterpartyMapper).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Ошибка обновление контаргента")
    void updateCounterparty_exception() {
        var request = new UpdateCounterpartyRequest().setId(null)
                .setPatch(CounterpartyFieldsPatch.builder().name("newName").build());

        mockMvc.perform(put("/api/v1/ui/counterparties", request)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.exClass").value("MethodArgumentNotValidException"));

        verify(counterpartyService, never()).updateCounterparty(any());
        verify(uiCounterpartyMapper, never()).apply(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Удаление контрагента")
    void deleteCounterparty() {
        var id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/ui/counterparties/{counterpartyId}", id)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(counterpartyService).deleteCounterpartyById(id);
    }
}
