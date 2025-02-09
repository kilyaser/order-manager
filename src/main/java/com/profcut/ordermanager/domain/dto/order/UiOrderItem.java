package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.domain.dto.machine.UiMachine;
import com.profcut.ordermanager.domain.dto.material.UiMaterial;
import com.profcut.ordermanager.domain.dto.product.UiProduct;
import com.profcut.ordermanager.domain.enums.PreparationState;
import com.profcut.ordermanager.domain.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
    @Schema(description = "Идентификор позиции заказа.", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;
    /**
     * Наименование изделия.
     */
    @Schema(description = "Наименование изделия.")
    private UiProduct product;
    /**
     * Колличество изделий.
     */
    @Schema(description = "Колличество изделий.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;
    /**
     * Колличество отгруженных позиций.
     */
    @Schema(description = "Колличество отгруженных позиций.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantityShipped;
    /**
     * Стоимость на одно изделие.
     */
    @Schema(description = "Стоимость на одно изделие.", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal pricePerProduct;
    /**
     * Общая стоимость изделия без НДС.
     */
    @Schema(description = "Общая стоимость изделия без НДС.", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal totalPrice;
    /**
     * Общая стоимость изделия c НДС.
     */
    @Schema(description = "Общая стоимость изделия c НДС.", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal currentSum;
    /**
     * Признак влкючения НДС в стоимость.
     */
    @Schema(description = "Признак влкючения НДС в стоимость.", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean isVatInclude;
    /**
     * Сумма НДС.
     */
    @Schema(description = "Сумма НДС.", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal vat;
    /**
     * Тип изделия.
     */
    @Schema(description = "Тип изделия.", requiredMode = Schema.RequiredMode.REQUIRED)
    private ProductType productType;
    /**
     * Признак написания управляющей программы.
     */
    @Schema(description = "Признак написания управляющей программы.", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean isProgramWritten;
    /**
     * id станка.
     */
    @Schema(description = "id станка.")
    private List<UiMachine> machines;
    /**
     * Дата завершения изготовления.
     */
    @Schema(description = "Дата завершения изготовления.")
    private LocalDateTime completionDate;
    /**
     * Статус готовности изделия.
     */
    @Schema(description = "Статус готовности изделия.", requiredMode = Schema.RequiredMode.REQUIRED)
    private PreparationState preparationState;
    /**
     * Тип материала.
     */
    @Schema(description = "Тип материала.")
    private UiMaterial material;
}
