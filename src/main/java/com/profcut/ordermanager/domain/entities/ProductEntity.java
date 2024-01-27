package com.profcut.ordermanager.domain.entities;

import com.profcut.ordermanager.domain.enums.MachineType;
import com.profcut.ordermanager.domain.enums.PreparationState;
import com.profcut.ordermanager.domain.enums.ProductType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {
    /**
     * Идентификор изделия.
     */
    @Id
    @GeneratedValue
    private UUID productId;
    /**
     * Наименование изделия.
     */
    private String productName;
    /**
     * Колличество изделий.
     */
    private Integer quantity;
    /**
     * Стоимость на одно изделие.
     */
    private BigDecimal pricePerProduct;
    /**
     * Общая стоимость изделия.
     */
    private BigDecimal totalPrice;
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
     * Тип станка.
     */
    @Enumerated(EnumType.STRING)
    private MachineType machineType;
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
    @ManyToOne
    @JoinColumn(name = "material_id")
    private MaterialEntity material;
    /**
     * Технолог.
     */
    @ManyToOne
    @JoinColumn(name = "technologist_id")
    private TechnologistEntity technologist;
    /**
     * Заказ.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public BigDecimal calculateTotalPrice() {
        return this.pricePerProduct.multiply(BigDecimal.valueOf(quantity));
    }
}
