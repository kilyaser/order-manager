package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.controllers.rest.ui.dto.technologist.UiTechnologist;
import com.profcut.ordermanager.controllers.rest.ui.mapper.UiTechnologistCreatorMapper;
import com.profcut.ordermanager.controllers.rest.ui.mapper.UiTechnologistMapper;
import com.profcut.ordermanager.servcie.TechnologistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ui/technologists")
@Tag(name = "technologist-ui-api", description = "Контроллер для получения информации о технологах")
public class TechnologistController {

    private final TechnologistService technologistService;
    private final UiTechnologistMapper uiTechnologistMapper;
    private final UiTechnologistCreatorMapper uiTechnologistCreatorMapper;


    @GetMapping("/{technologistId}")
    public UiTechnologist getTechnologist(@PathVariable UUID technologistId) {
        return uiTechnologistMapper.apply(technologistService.getById(technologistId));
    }

    @GetMapping("/{fullName}")
    public UiTechnologist getTechnologistByName(@PathVariable String fullName) {
        return uiTechnologistMapper.apply(technologistService.findByName(fullName));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UiTechnologist createTechnologist(@RequestBody CreateTechnologistRequest request) {
        var technologist = uiTechnologistCreatorMapper.apply(request);
        var savedTechnologist = technologistService.save(technologist);
        return uiTechnologistMapper.apply(savedTechnologist);
    }

    @DeleteMapping("/{technologistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTechnologist(@PathVariable UUID technologistId) {
        technologistService.deleteById(technologistId);
    }
}
