package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.domain.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.domain.dto.technologist.UiTechnologist;
import com.profcut.ordermanager.domain.dto.technologist.UpdateTechnologistRequest;
import com.profcut.ordermanager.controllers.rest.mapper.UiTechnologistMapper;
import com.profcut.ordermanager.service.TechnologistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/technologists")
@Tag(name = "technologist-ui-api", description = "Контроллер для управления информацией о технологах.")
public class TechnologistController {

    private final TechnologistService technologistService;
    private final UiTechnologistMapper uiTechnologistMapper;


    @GetMapping("/{technologistId}")
    @Operation(description = "Получить информацию о технологе.")
    public UiTechnologist getTechnologist(@PathVariable UUID technologistId) {
        return uiTechnologistMapper.apply(technologistService.getById(technologistId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Созадть технолога.")
    public UiTechnologist createTechnologist(@Valid @RequestBody CreateTechnologistRequest createRequest) {
        return uiTechnologistMapper.apply(technologistService.createTechnologist(createRequest));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновить данные технолога.")
    public UiTechnologist updateTechnologist(@Valid @RequestBody UpdateTechnologistRequest updateRequest) {
        return uiTechnologistMapper.apply(technologistService.updateTechnologist(updateRequest));
    }

    @DeleteMapping("/{technologistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить технолога.")
    public void deleteTechnologist(@PathVariable UUID technologistId) {
        technologistService.deleteById(technologistId);
    }
}
