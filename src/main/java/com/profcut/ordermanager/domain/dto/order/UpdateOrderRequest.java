package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "UpdateOrderRequest")
public class UpdateOrderRequest {
    /**
     * ID заказа.
     */
    @NotNull
    @Schema(description = "id заказа.", maxLength = DataTypes.UUID_LENGTH)
    private UUID id;
    /**
     * Обновленные атрибуты заказа.
     */
    @NotNull
    @Schema(description = "Обновленные атрибуты заказа")
    private OrderFieldsPatch patch;
}
