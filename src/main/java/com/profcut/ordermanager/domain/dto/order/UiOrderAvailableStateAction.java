package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.enums.OrderState;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "UiOrderItem")
public class UiOrderAvailableStateAction {
    /**
     * Доступные статусы заказа для назначения.
     */
    @ArraySchema(schema = @Schema(description = "Доступные статусы заказа для назначения.",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE, requiredMode = Schema.RequiredMode.REQUIRED))
    private List<OrderState> availableState;
}
