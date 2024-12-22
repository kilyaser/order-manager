package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.domain.dto.constraint.AdminConstraint;
import com.profcut.ordermanager.service.impl.AdminConstraintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/admin")
@Tag(name = "admin-constraint-api", description = "Контролер для получения информации об ограничениях функционала.")
public class AdminConstraintsApi {

    private final AdminConstraintService adminConstraintService;

    @GetMapping("/constraints")
    @Operation(description = "Получить информацию об ограничениях.")
    public AdminConstraint getConstraints() {
        return adminConstraintService.getAdminConstraints();
    }
}
