package com.profcut.ordermanager.controllers.rest.auth;

import com.profcut.ordermanager.domain.dto.auth.OmUser;
import com.profcut.ordermanager.domain.dto.auth.PasswordUpdateRequest;
import com.profcut.ordermanager.domain.dto.auth.UpdateOmUserRequest;
import com.profcut.ordermanager.controllers.rest.mapper.OmUserMapper;
import com.profcut.ordermanager.domain.enums.Constants;
import com.profcut.ordermanager.security.service.OmUserService;
import com.profcut.ordermanager.security.service.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "om-user-api", description = "Конроллер для манипуляции данными пользователя")
public class OmUserController {

    private final OmUserService omUserService;
    private final OmUserMapper omUserMapper;
    private final PasswordService passwordService;

    @PutMapping("/change")
    @Operation(description = "Обновление данных пользователя")
    public OmUser changeOmUser(@Valid @RequestBody UpdateOmUserRequest request) {
        return omUserMapper.apply(omUserService.updateOmUser(request));
    }

    @PutMapping("/password")
    @Operation(description = "Изменение пароля.")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordUpdateRequest request) {
        if (passwordService.updatePassword(request)) {
            return ResponseEntity.ok(Constants.PASSWORD_UPDATED.getValue());
        } else {
            return ResponseEntity.ok(Constants.PASSWORD_UPDATE_ERROR.getValue());
        }
    }

    @GetMapping("/{userId}")
    public OmUser getOmUser(@PathVariable UUID userId) {
        return omUserMapper.apply(omUserService.findById(userId));
    }
}
