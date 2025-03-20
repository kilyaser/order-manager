package com.profcut.ordermanager.domain.dto.material;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "UiEstablishMachine")
public class UiEstablishMachineRequest {
    /**
     * Идентификатор позиции заказа.
     */
    @NotNull
    @Schema(description = "id позиции заказа.", maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID orderItemId;
    /**
     * Идентификаторы станков.
     */
    @ArraySchema(schema = @Schema(description = "id станков",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private Set<UUID> machineIds;
}
