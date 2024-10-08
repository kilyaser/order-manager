package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class OmUserMapperTest {

    OmRoleMapper roleMapper = Mappers.getMapper(OmRoleMapper.class);

    @InjectMocks
    OmUserMapperImpl mapper;

    @Test
    void shouldMapAllFields() {
        var user = new OmUserEntity()
                .setId(UUID.randomUUID())
                .setEmail("test@email.ru")
                .setPhone("+11111111111")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setPatronymic("patronymic")
                .setRoles(Set.of(
                        new OmRoleEntity().setId(UUID.randomUUID()).setRole(OmRole.MANAGER),
                        new OmRoleEntity().setId(UUID.randomUUID()).setRole(OmRole.CEO)));
        var result = mapper.apply(user);
        assertNotNull(result);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("password", "isBlock", "isDeleted", "roles")
                .isEqualTo(user);
    }
}
