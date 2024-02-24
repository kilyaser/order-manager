package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.controllers.rest.dto.auth.RegisterRequest;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class OmUserCreateMapperTest {

    OmUserCreateMapper mapper = Mappers.getMapper(OmUserCreateMapper.class);

    @Test
    void shouldMapAllFields() {
        var request = RegisterRequest.builder()
                .firstName("firstname")
                .lastName("lastname")
                .email("email")
                .phone("1111")
                .password("password")
                .roles(Set.of(OmRole.ROLE_MANAGER, OmRole.ROLE_TECHNOLOGIST))
                .build();
        var result = mapper.apply(request);

        assertNotNull(result);
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getEmail(), result.getEmail());
        assertEquals(request.getPhone(), result.getPhone());
        assertNull(result.getPassword());
        assertNull(result.getRoles());
    }
}
