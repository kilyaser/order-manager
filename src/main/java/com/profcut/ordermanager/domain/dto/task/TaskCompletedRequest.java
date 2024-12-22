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
@Schema(description = "TaskCompletedRequest")
public class TaskCompletedRequest {

    /**
     * Идентификатор задачи.
     */
    @NotNull
    @Schema(description = "Идентификатор задачи.",
            maxLength = DataTypes.UUID_LENGTH)
    private UUID taskId;
    /**
     * Признак завершения задачи.
     */
    @Schema(description = "Признак завершения задачи.")
    private boolean completed;
}
