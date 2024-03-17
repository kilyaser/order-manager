package com.profcut.ordermanager.security.service;

import com.profcut.ordermanager.ConfigurationTestBeans;
import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import com.profcut.ordermanager.security.domain.model.repository.OmRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.stream.Collectors;

import static com.profcut.ordermanager.security.domain.model.enums.OmRole.ROLE_ADMIN;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.ROLE_CEO;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.ROLE_MANAGER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ConfigurationTestBeans.class)
public class RoleServiceTest {

    @Autowired
    OmRoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @ParameterizedTest
    @EnumSource(OmRole.class)
    void findByRoleName(OmRole role) {
        var roleDb = roleService.findByRoleName(role);

        assertNotNull(roleDb);
        assertEquals(role.name(), roleDb.getRole().name());
    }

    @Test
    void fineRoles() {
        var roles = Set.of(ROLE_ADMIN, ROLE_MANAGER, ROLE_CEO);

        var result = roleService.findRoles(roles);
        var rolesDb = result.stream().map(OmRoleEntity::getRole).collect(Collectors.toSet());

        assertEquals(roles.size(), result.size());
        assertTrue(rolesDb.containsAll(roles));
    }
}
