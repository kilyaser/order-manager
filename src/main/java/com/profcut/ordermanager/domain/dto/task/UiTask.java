package com.profcut.ordermanager.domain.dto.task;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "UiTask")
public class UiTask {
    /**
     * Идентификатор задачи.
     */
    @Schema(description = "Идентификатор задачи.")
    private UUID id;
    /**
     * Описание задачи.
     */
    @Schema(description = "Описание задачи.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String description;
    /**
     * Признак завершения задачи.
     */
    @Schema(description = "Признак завершения задачи.")
    private boolean isCompleted;
}
