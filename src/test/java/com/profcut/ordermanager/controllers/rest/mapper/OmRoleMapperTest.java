package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OmRoleMapperTest {

    OmRoleMapper mapper = Mappers.getMapper(OmRoleMapper.class);

    @Test
    void shouldMapAllFields() {
        var role = new OmRoleEntity().setId(UUID.randomUUID())
                .setRole(OmRole.ROLE_MANAGER)
                .setDescription("some description");

        var result = mapper.apply(role);
        assertNotNull(result);
        assertEquals(role.getRole().name(), result.getRole());
    }
}
