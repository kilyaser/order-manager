package com.profcut.ordermanager.domain.entities;

import com.profcut.ordermanager.domain.enums.ContractState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "contracts")
public class ContractEntity {
    /**
     * Идентификатор контракта.
     */
    @Id
    @UuidGenerator
    private UUID contractId;
    /**
     * Номер договора.
     */
    private String contractNumber;
    /**
     * Дата договора.
     */
    private LocalDate contractDate;
    /**
     * Дата, с которой договор вступает в силу.
     */
    private LocalDate startDate;
    /**
     * Дата окончания действия договора
     */
    private LocalDate endDate;
    /**
     * Контрагент - заказчик.
     */
    @ManyToOne
    @JoinColumn(name = "counterparty_id")
    private CounterpartyEntity counterparty;
    /**
     * Статус договора.
     */
    @Enumerated(EnumType.STRING)
    private ContractState status;
    /**
     * Примечания.
     */
    private String notes;
    /**
     * Заказы по договору.
     */
    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "contract")
    private List<OrderEntity> orders;
}
