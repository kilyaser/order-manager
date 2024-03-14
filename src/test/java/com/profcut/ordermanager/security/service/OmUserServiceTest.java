package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.ConfigurationTestBeans;
import com.profcut.ordermanager.domain.dto.auth.OmUserFieldPatch;
import com.profcut.ordermanager.domain.dto.auth.UpdateOmUserRequest;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.domain.model.repository.OmUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql("/sql/om-user-test.sql")
@SpringBootTest(classes = ConfigurationTestBeans.class)
public class OmUserServiceTest {

    @Autowired
    OmUserRepository repository;
    @Autowired
    OmUserService omUserService;

    @Test
    @DisplayName("Успешное обновление юзера")
    void updateOmUser() {
        var id = UUID.fromString("54e04691-2fd7-4b9b-9768-754cf022fb38");
        var patch = OmUserFieldPatch.builder()
                .phone("88888888888")
                .email("new@mail.ru")
                .build();
        var userDb = repository.findById(id).orElse(new OmUserEntity());
        var updateRequest = new UpdateOmUserRequest().setId(id).setPatch(patch);
        var updatedOmUser = omUserService.updateOmUser(updateRequest);

        assertNotNull(updatedOmUser);
        assertNotNull(updatedOmUser.getId());
        assertEquals(patch.getEmail(), updatedOmUser.getEmail());
        assertEquals(patch.getPhone(), updatedOmUser.getPhone());
        assertEquals(userDb.getFirstName(), updatedOmUser.getFirstName());
    }
}
