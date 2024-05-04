package com.profcut.ordermanager.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "payments")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class PaymentEntity {
    /**
     * Идентификатор платежа.
     */
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private UUID paymentId;
    /**
     * Контрагент - плательщик.
     */
    @ManyToOne
    @JoinColumn(name = "counterparty_id")
    private CounterpartyEntity counterparty;
    /**
     * Заказ.
     */
    @ManyToOne
    @ToString.Exclude
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    /**
     * Размер платежа.
     */
    private BigDecimal paymentSum;
    /**
     * Дата платежа
     */
    private LocalDateTime paymentDate;
    /**
     * Дата последенго изменения.
     */
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
