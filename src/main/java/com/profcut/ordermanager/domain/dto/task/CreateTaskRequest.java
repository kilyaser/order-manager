package com.profcut.ordermanager.domain.dto.task;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CreateTaskRequest")
public class CreateTaskRequest {
    /**
     * Идентификатор заказа.
     */
    @NotNull
    @Schema(description = "Идентификатор заказа.",
            maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID orderId;
    /**
     * Описание задачи.
     */
    @Schema(description = "Описание задачи",
            maxLength = DataTypes.STRING_LENGTH_MAX)
    private String description;
}
