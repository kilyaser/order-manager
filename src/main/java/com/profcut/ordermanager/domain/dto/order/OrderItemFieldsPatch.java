package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.enums.PreparationState;
import com.profcut.ordermanager.domain.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "OrderItemFieldsPatch")
public class OrderItemFieldsPatch {
    /**
     * Идентификор позиции заказа.
     */
    @NotNull
    @Schema(description = "Идентификор позиции заказа.")
    private UUID itemId;
    /**
     * Идентификатор изделия.
     */
    @Schema(description = "id изделия", maxLength = DataTypes.UUID_LENGTH)
    private UUID productId;
    /**
     * Колличество изделий.
     */
    @Schema(description = "Колличество изделий.")
    @Min(0)
    private Integer quantity;
    /**
     * Колличество отгруженных позиций.
     */
    @Schema(description = "Колличество отгруженных позиций.")
    @Min(0)
    private Integer quantityShipped;
    /**
     * Стоимость на одно изделие.
     */
    @Schema(description = "Стоимость на одно изделие.")
    private BigDecimal pricePerProduct;
    /**
     * Признак влкючения НДС в стоимость.
     */
    @Schema(description = "Признак влкючения НДС в стоимость.")
    private Boolean isVatInclude;
    /**
     * Тип изделия.
     */
    @Schema(description = "Тип изделия.")
    private ProductType productType;
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
     * Id материала.
     */
    @Schema(description = "Id материала.")
    private UUID materialId;
    /**
     * ID технолога.
     */
    private UUID technologistId;
}
