package com.profcut.ordermanager.domain.entities;

import com.profcut.ordermanager.domain.enums.PreparationState;
import com.profcut.ordermanager.domain.enums.ProductType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.profcut.ordermanager.common.consts.OrderConstant.VAT_DIVIDER;
import static com.profcut.ordermanager.common.consts.OrderConstant.VAT_RATE;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "order_items")
@EntityListeners(AuditingEntityListener.class)
public class OrderItemEntity {
    /**
     * Идентификор позиции заказа.
     */
    @Id
    @UuidGenerator
    private UUID id;
    /**
     * Наименование изделия.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    /**
     * Колличество изделий.
     */
    private Integer quantity;
    /**
     * Колличество отгруженных позиций.
     */
    private Integer quantityShipped;
    /**
     * Стоимость на одно изделие.
     */
    private BigDecimal pricePerProduct;
    /**
     * Общая стоимость изделия без НДС.
     */
    private BigDecimal totalPrice;
    /**
     * Общая стоимость изделия c НДС.
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
     * Тип изделия.
     */
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    /**
     * Признак написания управляющей программы.
     */
    private boolean isProgramWritten;
    /**
     * id станка.
     */
    @OneToMany(mappedBy = "orderItem")
    @Fetch(FetchMode.JOIN)
    private Set<CncMachineEntity> machines = new HashSet<>();
    /**
     * Дата завершения изготовления.
     */
    private LocalDateTime completionDate;
    /**
     * Статус готовности изделия.
     */
    @Enumerated(EnumType.STRING)
    private PreparationState preparationState;
    /**
     * Тип материала.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "material_id")
    private MaterialEntity material;
    /**
     * Заказ.
     */
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public void calculateTotalPrice() {
        this.totalPrice = this.pricePerProduct.multiply(BigDecimal.valueOf(quantity));
        calculateVat();
        calculateCurrentSum();
    }

    public void addMachine(List<CncMachineEntity> machines) {
        machines.forEach(machine -> {
            machine.setOrderItem(this);
            machine.setOrder(this.order);
            this.machines.addAll(machines);
        });
    }

    public void calculateCurrentSum() {
        if (isVatInclude) {
            currentSum = totalPrice;
        } else  {
            currentSum = totalPrice.add(vat);
        }
    }

    public void calculateVat() {
        if (Objects.isNull(getTotalPrice()) || getTotalPrice().equals(BigDecimal.ZERO)) {
            vat = BigDecimal.ZERO;
        }
        if (isVatInclude) {
            vat = getTotalPrice().divide(VAT_DIVIDER, RoundingMode.HALF_UP).multiply(VAT_RATE);
        } else {
            vat = getTotalPrice().multiply(VAT_RATE);
        }
    }
}
