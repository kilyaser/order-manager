package com.profcut.ordermanager.controllers.rest.auth;

import com.profcut.ordermanager.domain.dto.auth.OmUser;
import com.profcut.ordermanager.domain.dto.auth.UpdateOmUserRequest;
import com.profcut.ordermanager.controllers.rest.mapper.OmUserMapper;
import com.profcut.ordermanager.security.service.OmUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "om-user-api", description = "Конроллер для манипуляции данными пользователя")
public class OmUserController {

    private final OmUserService omUserService;
    private final OmUserMapper omUserMapper;

    @PutMapping("/change")
    public OmUser changeOmUser(@Valid @RequestBody UpdateOmUserRequest request) {
        return omUserMapper.apply(omUserService.updateOmUser(request));
    }
}
