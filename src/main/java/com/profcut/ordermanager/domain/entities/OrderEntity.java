package com.profcut.ordermanager.domain.entities;


import com.profcut.ordermanager.domain.enums.MasterStatus;
import com.profcut.ordermanager.domain.enums.OrderState;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity {
    /**
     * Идентификатор заказа.
     */
    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator
    private UUID orderId;
    /**
     * Номер заказа.
     */
    private String orderNumber;
    /**
     * Наименование заказа.
     */
    private String orderName;
    /**
     * Номер счета.
     */
    private String billNumber;
    /**
     * Ссылка на рабочую папку.
     */
    private String workFolderLink;
    /**
     * Сумма действующая.
     */
    private BigDecimal currentSum;
    /**
     * Сумма долга.
     */
    private BigDecimal debtSum;
    /**
     * Статус заказа.
     */
    @Enumerated(STRING)
    private OrderState orderState;
    /**
     * Мастер статус заказа.
     */
    @Enumerated(STRING)
    private MasterStatus masterStatus;
    /**
     * Дата завершеня заказа.
     */
    private LocalDateTime completionDate;
    /**
     * Признак государственного заказа.
     */
    private boolean isGovernmentOrder;
    /**
     *  Дата создания заказа.
     */
    @CreatedDate
    private LocalDateTime createdDate;
    /**
     * Дата последнего изменения.
     */
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    /**
     *  Контрагент заказчик.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counterparty_id")
    private CounterpartyEntity counterparty;
    /**
     * Признак, удаленный заказ.
     */
    private boolean isDeleted;
    /**
     * Изделия относящиеся к заказу.
     */
    @OneToMany(mappedBy = "order")
    private Set<ProductEntity> products = new HashSet<>();
    /**
     * Платежи по заказу
     */
    @OneToMany(mappedBy = "order")
    private Set<PaymentEntity> payments = new HashSet<>();
    /**
     * Задачи по заказу.
     */
    @OneToMany(mappedBy = "order")
    private Set<TaskEntity> tasks = new HashSet<>();
}
