package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.enums.MachineType;
import com.profcut.ordermanager.domain.enums.PreparationState;
import com.profcut.ordermanager.domain.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "OrderItemRequest")
public class OrderItemRequest {
    /**
     * Id изделие (детали).
     */
    @NotNull
    @Schema(description = "Id изделие (детали)")
    private UUID productId;
    /**
     * Тип изделия.
     */
    @Schema(description = "Тип изделия.")
    private ProductType productType;
    /**
     * Количество изделий.
     */
    @Schema(description = "Количество изделий.")
    private Integer quantity;
    /**
     * Колличество отгруженных позиций.
     */
    @Schema(description = "Колличество отгруженных позиций.")
    private Integer quantityShipped;
    /**
     * Стоимость за одну позицию.
     */
    @Schema(description = "Стоимость за одну позицию.")
    private BigDecimal pricePerProduct;
    /**
     * Стоимсть всей позиции.
     */
    @Schema(description = "Стоимсть всей позиции.")
    private BigDecimal totalPrice;
    /**
     * Признак влкючения НДС в стоимость.
     */
    @Schema(description = "Признак влкючения НДС в стоимость.")
    private boolean isVatInclude;
    /**
     * Признак написания управляющей программы.
     */
    @Schema(description = "Признак написания управляющей программы.")
    private boolean isProgramWritten;
    /**
     * Тип станка.
     */
    @Schema(description = "Тип станка.")
    private MachineType machineType;
    /**
     * Статус готовности изделия.
     */
    @Schema(description = "Статус готовности изделия.")
    private PreparationState preparationState;
    /**
     * Дата завершения изготовления.
     */
    @Schema(description = "Дата завершения изготовления.")
    private LocalDateTime completionDate;
    /**
     * Id материала детали (изделия).
     */
    @Schema(description = "Id материала детали.")
    private UUID materialId;
    /**
     * ID Технолога.
     */
    @Schema(description = "ID Технолога.", maxLength = DataTypes.UUID_LENGTH)
    private UUID technologistId;
}
