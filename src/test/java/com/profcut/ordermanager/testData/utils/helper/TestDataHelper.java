package com.profcut.ordermanager.testData.utils.helper;

import com.profcut.ordermanager.domain.dto.auth.AuthRequest;
import com.profcut.ordermanager.domain.dto.auth.AuthResponse;
import com.profcut.ordermanager.domain.dto.auth.OmUser;
import com.profcut.ordermanager.domain.dto.auth.RegisterRequest;
import com.profcut.ordermanager.domain.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.profcut.ordermanager.security.domain.model.enums.OmRole.ADMIN;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.CEO;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.MANAGER;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.TECHNOLOGIST;

public class TestDataHelper {

    private static final Map<OmRole, OmRoleEntity> rolesMap = Map.of(
            ADMIN, new OmRoleEntity(UUID.randomUUID(), ADMIN, "admin"),
            MANAGER, new OmRoleEntity(UUID.randomUUID(), MANAGER, "manager"),
            CEO, new OmRoleEntity(UUID.randomUUID(), CEO, "ceo"),
            TECHNOLOGIST, new OmRoleEntity(UUID.randomUUID(), TECHNOLOGIST, "technologist")
    );

    public static RegisterRequest getDefaultRegisterRequest() {
        return RegisterRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .email("test@mail.ru")
                .password("Password1")
                .build();
    }

    public static OmUser getDefaultOmUser(RegisterRequest request, UUID id) {
        return OmUser.builder()
                .id(id)
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .email(request.getEmail())
                .build();
    }

    public static OmUserEntity getDefaultOmUserEntity() {
        return new OmUserEntity()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setPatronymic("patronymic")
                .setEmail("test@mail.ru")
                .setPhone("+11111111111");
    }

    public static Set<OmRoleEntity> getSelectedRole(OmRole role) {
        return Set.of(rolesMap.get(role));
    }

    public static AuthRequest getDefaultAuthRequest() {
        return AuthRequest.builder()
                .email("default@mail.ru")
                .password("Password1")
                .build();
    }

    public static AuthResponse getDefaultAuthResponse() {
        return AuthResponse.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
    }

    public static CreateTechnologistRequest getDefaultCreateTechnologistRequest() {
        return CreateTechnologistRequest.builder()
                .firstName("Name")
                .lastName("lastName")
                .patronymic("Patronymic")
                .email("test@email.ru")
                .phone("+711111")
                .build();
    }
}
