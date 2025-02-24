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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
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
    /**
     * Договора контрагента.
     */
    @ToString.Exclude
    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "counterparty")
    private Set<ContractEntity> contracts = new HashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CounterpartyEntity that = (CounterpartyEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
