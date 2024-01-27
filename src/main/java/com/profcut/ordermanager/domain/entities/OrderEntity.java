package com.profcut.ordermanager.domain.entities;


import com.profcut.ordermanager.domain.entities.enums.MasterStatus;
import com.profcut.ordermanager.domain.entities.enums.OrderState;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;


import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(
            name = "uuid-hibernate-generator",
            strategy = "org.hibernate.id.UUIDGenerator")
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
}
