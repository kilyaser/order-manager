package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "UiOrderItems")
public class UiOrderItems {
    /**
     * Идентификор заказа.
     */
    @Schema(description = "id заказа.", maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID orderId;
    /**
     * Изделия относящиеся к заказу.
     */
    @ArraySchema(schema = @Schema(description = "Изделия относящиеся к заказу.",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<UiOrderItem> orderItems;
}
