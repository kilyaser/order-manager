package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeleteOrderItemRequest {
    /**
     * Идентификор заказа.
     */
    @NotNull
    @Schema(description = "id заказа.", maxLength = DataTypes.UUID_LENGTH)
    private UUID orderId;
    /**
     * id позиций для удаления.
     */
    @NotNull
    @ArraySchema(schema = @Schema(description = "id позиций для удаления.",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private Set<UUID> deleteItemIds;
}
