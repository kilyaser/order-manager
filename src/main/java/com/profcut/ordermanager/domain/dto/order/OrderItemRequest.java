package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.dto.material.UiMaterial;
import com.profcut.ordermanager.domain.dto.product.UiProduct;
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
    @NotNull
    @Schema(description = "Количество изделий.")
    private Integer quantity;
    /**
     * Стоимость за одну позицию.
     */
    @NotNull
    @Schema(description = "Стоимость за одну позицию.")
    private BigDecimal pricePerProduct;
    /**
     * Стоимсть всей позиции.
     */
    @NotNull
    @Schema(description = "Стоимсть всей позиции.")
    private BigDecimal totalPrice;
    /**
     * Признак влкючения НДС в стоимость.
     */
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
     * Материал детали (изделия).
     */
    @Schema(description = "Материал детали.")
    private UiMaterial material;
    /**
     * ID Технолога.
     */
    @Schema(description = "ID Технолога.", maxLength = DataTypes.UUID_LENGTH)
    private UUID technologistId;
}
