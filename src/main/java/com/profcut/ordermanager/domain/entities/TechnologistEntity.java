package com.profcut.ordermanager.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "technologists")
public class TechnologistEntity {
    /**
     * Идентификатор технолога.
     */
    @Id
    @UuidGenerator
    private UUID id;
    /**
     * Имя технолога.
     */
    private String firstName;
    /**
     * Фамилия пользователя.
     */
    private String lastName;
    /**
     * Отчество пользователя.
     */
    private String patronymic;
    /**
     * e-mail технолога.
     */
    private String email;
    /**
     * Контактный телефон.
     */
    private String phone;
    /**
     * Перечень изделий.
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "technologist")
    private Set<ProductEntity> products = new HashSet<>();
}
