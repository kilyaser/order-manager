package com.profcut.ordermanager.controllers.rest.auth;

import com.profcut.ordermanager.domain.dto.auth.RolesResponse;
import com.profcut.ordermanager.security.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/roles")
@Tag(name = "role-controller-api", description = "Контролер для получения информации о ролях.")
public class OmRoleController {

    private final RoleService roleService;

    @GetMapping
    @Operation(description = "Получить доступные для назначения роли.")
    @PreAuthorize("@adminConstraintService.canViewRoles()")
    public RolesResponse getAvailableRoles() {
        return RolesResponse.builder()
                .availableRoles(roleService.findAll())
                .build();
    }
}
