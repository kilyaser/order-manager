package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.rest.mapper.UiMaterialMapper;
import com.profcut.ordermanager.domain.dto.material.CreateMaterialRequest;
import com.profcut.ordermanager.domain.dto.material.UiMaterial;
import com.profcut.ordermanager.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1/ui/materials")
@Tag(name = "material-ui-api", description = "Контроллер для управления материалами")
public class MaterialController {

    private final UiMaterialMapper uiMaterialMapper;
    private final MaterialService materialService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создание материала.")
    public UiMaterial createMaterial(@Valid @RequestBody CreateMaterialRequest request) {
        return uiMaterialMapper.apply(materialService.createMaterial(request));
    }

    @GetMapping("/{materialId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Получение материала по id")
    public UiMaterial getMaterialById(@PathVariable UUID materialId) {
        return uiMaterialMapper.apply(materialService.findById(materialId));
    }
}
