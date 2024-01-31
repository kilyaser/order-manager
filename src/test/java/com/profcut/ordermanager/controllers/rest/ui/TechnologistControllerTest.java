package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.exception.ErrorHttpResponseFactory;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    MockMvc mockMvc;

    @Test
    void should_return_404_not_found_when_get_technologist() throws Exception {
        var id = UUID.randomUUID();
        when(technologistService.getById(id)).thenThrow(TechnologistNotFoundException.class);

        this.mockMvc.perform(get("/ui/technologists/{technologistId}", id))
                .andExpect(status().isNotFound());

        verify(uiTechnologistMapper, never()).apply(any());
    }

    @Test
    void should_return_ok_when_get_technologist() throws Exception {
        var id = UUID.randomUUID();
        var dto = new UiTechnologist().setId(id);

        when(technologistService.getById(id)).thenReturn(new TechnologistEntity().setId(id));
        when(uiTechnologistMapper.apply(any())).thenReturn(dto);

        this.mockMvc.perform(get("/ui/technologists/{technologistId}", id))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.id").value(id.toString())
                );

        verify(technologistService).getById(any());
        verify(uiTechnologistMapper).apply(any());
    }
}
