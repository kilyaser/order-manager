package com.profcut.ordermanager.domain.entities;

import com.profcut.ordermanager.domain.enums.MasterStatus;
import com.profcut.ordermanager.domain.enums.OrderState;
import com.profcut.ordermanager.domain.exceptions.VatCalculationException;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.profcut.ordermanager.common.consts.OrderConstant.VAT_DIVIDER;
import static com.profcut.ordermanager.common.consts.OrderConstant.VAT_RATE;
import static jakarta.persistence.EnumType.STRING;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderEntity {
    /**
     * Идентификатор заказа.
     */
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
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
     * Признак влкючения НДС в стоимость.
     */
    private boolean isVatInclude;
    /**
     * Сумма НДС.
     */
    private BigDecimal vat;
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
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "counterparty_id")
    private CounterpartyEntity counterparty;
    /**
     * Логин пользователя создавшего заказ.
     */
    private String author;
    /**
     * Признак, удаленный заказ.
     */
    private boolean isDeleted;
    /**
     * Изделия относящиеся к заказу.
     */
    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> orderItems = new ArrayList<>();
    /**
     * Платежи по заказу
     */
    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "order")
    private Set<PaymentEntity> payments = new HashSet<>();
    /**
     * Задачи по заказу.
     */
    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "order")
    private List<TaskEntity> tasks = new ArrayList<>();

    public void addItems(List<OrderItemEntity> items) {
        items.forEach(item -> {
            item.setOrder(this);
            this.orderItems.add(item);
        });
        this.recalculateCurrentSum();
    }

    public void addPayment(PaymentEntity payment) {
        payment.setOrder(this);
        payments.add(payment);
        calculateDebtSum();
    }

    public void removePayment(PaymentEntity payment) {
        getPayments().removeIf(p -> p.getPaymentId().equals(payment.getPaymentId()));
        calculateDebtSum();
    }

    public OrderEntity recalculateCurrentSum() {
        if (orderItems.isEmpty()) {
            currentSum = BigDecimal.ZERO;
            return this.calculateVat().calculateDebtSum();
        }
        currentSum = orderItems.stream()
                .map(product -> product.getPricePerProduct().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return this.calculateVat().calculateDebtSum();
    }

    public OrderEntity calculateVat() {
        if (Objects.isNull(currentSum) || currentSum.equals(BigDecimal.ZERO)) {
            vat = BigDecimal.ZERO;
            return this;
        }
        if (isVatInclude) {
            vat = currentSum.divide(VAT_DIVIDER, RoundingMode.HALF_UP).multiply(VAT_RATE);
        } else {
            vat = currentSum.multiply(VAT_RATE);
        }
        return this;
    }

    public OrderEntity calculateDebtSum() {
        if (payments.isEmpty()) {
            debtSum = currentSum;
            return this;
        }
        var paymentsSum = payments.stream()
                .map(PaymentEntity::getPaymentSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        debtSum = currentSum.subtract(paymentsSum);
        return this;
    }
}
