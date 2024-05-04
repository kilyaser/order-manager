package com.profcut.ordermanager.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "counterparties")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CounterpartyEntity {
    /**
     * Идентификор контрагента.
     */
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private UUID id;
    /**
     * Полное наименование контрагента.
     */
    private String fullName;
    /**
     * Краткое наименование контрагента.
     */
    private String name;
    /**
     * ИНН контрагента.
     */
    @EqualsAndHashCode.Include
    private String inn;
    /**
     * е-mail контрагента.
     */
    private String email;
    /**
     * Контактный телефон контрагента.
     */
    private String phone;
    /**
     * Заказы контрагента.
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "counterparty")
    private Set<OrderEntity> orders = new HashSet<>();
}
