package com.profcut.ordermanager.security.domain.model.entity;

import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "om_roles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OmRoleEntity {

    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OmRole role;

    private String description;
}
