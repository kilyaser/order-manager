package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.domain.dto.material.UiMaterial;
import com.profcut.ordermanager.domain.dto.product.UiProduct;
import com.profcut.ordermanager.domain.dto.technologist.UiTechnologist;
import com.profcut.ordermanager.domain.enums.MachineType;
import com.profcut.ordermanager.domain.enums.PreparationState;
import com.profcut.ordermanager.domain.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "UiOrderItem")
public class UiOrderItem {
    /**
     * Идентификор позиции заказа.
     */
    @Schema(description = "Идентификор позиции заказа.")
    private UUID id;
    /**
     * Наименование изделия.
     */
    @Schema(description = "Наименование изделия.")
    private UiProduct product;
    /**
     * Колличество изделий.
     */
    @Schema(description = "Колличество изделий.")
    private Integer quantity;
    /**
     * Колличество отгруженных позиций.
     */
    @Schema(description = "Колличество отгруженных позиций.")
    private Integer quantityShipped;
    /**
     * Стоимость на одно изделие.
     */
    @Schema(description = "Стоимость на одно изделие.")
    private BigDecimal pricePerProduct;
    /**
     * Общая стоимость изделия.
     */
    @Schema(description = "Общая стоимость изделия.")
    private BigDecimal totalPrice;
    /**
     * Признак влкючения НДС в стоимость.
     */
    @Schema(description = "Признак влкючения НДС в стоимость.")
    private boolean isVatInclude;
    /**
     * Сумма НДС.
     */
    @Schema(description = "Сумма НДС.")
    private BigDecimal vat;
    /**
     * Тип изделия.
     */
    @Schema(description = "Тип изделия.")
    private ProductType productType;
    /**
     * Признак написания управляющей программы.
     */
    @Schema(description = "Признак написания управляющей программы.")
    private boolean isProgramWritten;
    /**
     * id станка.
     */
    @Schema(description = "id станка.")
    private UUID machineId;
    /**
     * Дата завершения изготовления.
     */
    @Schema(description = "Дата завершения изготовления.")
    private LocalDateTime completionDate;
    /**
     * Статус готовности изделия.
     */
    @Schema(description = "Статус готовности изделия.")
    private PreparationState preparationState;
    /**
     * Тип материала.
     */
    @Schema(description = "Тип материала.")
    private UiMaterial material;
    /**
     * Технолог.
     */
    @Schema(description = "Технолог.")
    private UiTechnologist technologist;
}
