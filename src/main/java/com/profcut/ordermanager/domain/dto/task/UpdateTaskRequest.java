package com.profcut.ordermanager.domain.dto.task;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "UpdateTaskRequest")
public class UpdateTaskRequest {

    /**
     * Идентификатор задачи.
     */
    @NotNull
    @Schema(description = "Идентификатор задачи.",
            maxLength = DataTypes.UUID_LENGTH)
    private UUID taskId;
    /**
     * Описание задачи.
     */
    @NotBlank
    @Schema(description = "Описание задачи",
            maxLength = DataTypes.STRING_LENGTH_MAX)
    private String description;
}
