package com.profcut.ordermanager.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "materials")
public class MaterialEntity {
    /**
     * Идентификатор материала.
     */
    @Id
    @UuidGenerator
    private UUID id;
    /**
     * Тип материала.
     */
    private String materialType;
}
