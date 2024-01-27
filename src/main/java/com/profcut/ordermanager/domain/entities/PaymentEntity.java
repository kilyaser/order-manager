package com.profcut.ordermanager.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
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
@EntityListeners(AuditingEntityListener.class)
public class PaymentEntity {
    /**
     * Идентификатор платежа.
     */
    @Id
    @GeneratedValue
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
