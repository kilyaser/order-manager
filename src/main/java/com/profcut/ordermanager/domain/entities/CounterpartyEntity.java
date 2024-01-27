package com.profcut.ordermanager.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "counterparties")
public class CounterpartyEntity {
    /**
     * Идентификор контрагента.
     */
    @Id
    @GeneratedValue
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
    @OneToMany(mappedBy = "counterparty")
    private Set<OrderEntity> orders = new HashSet<>();
}
