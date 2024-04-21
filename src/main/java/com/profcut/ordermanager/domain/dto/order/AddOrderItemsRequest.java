package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "AddOrderItemsRequest")
public class AddOrderItemsRequest {
    /**
     * Идентификор заказа.
     */
    @NotNull
    @Schema(description = "id заказа.", maxLength = DataTypes.UUID_LENGTH)
    private UUID orderId;
    /**
     * Список позиций заказа.
     */
    @Valid
    @NotNull
    @ArraySchema(schema = @Schema(description = "Список позиций заказа.",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<OrderItemRequest> itemsRequest;
}
