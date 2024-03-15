package com.profcut.ordermanager.testData.utils;

import com.profcut.ordermanager.domain.dto.auth.OmUser;
import com.profcut.ordermanager.domain.dto.auth.RegisterRequest;

import java.util.UUID;

public class TestDataHelper {

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
}
