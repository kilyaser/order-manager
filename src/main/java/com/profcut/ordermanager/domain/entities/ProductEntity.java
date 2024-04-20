package com.profcut.ordermanager.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {

    /**
     * Идентификатор изделия.
     */
    @Id
    @UuidGenerator
    private UUID productId;
    /**
     * Наименование изделия.
     */
    private String productName;
}
