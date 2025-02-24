package com.profcut.ordermanager.domain.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
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
    @Schema(description = "Идентификатор задачи.", maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;
    /**
     * Описание задачи.
     */
    @Schema(description = "Описание задачи.", maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    /**
     * Признак завершения задачи.
     */
    @Schema(description = "Признак завершения задачи.", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("isCompleted")
    private boolean isCompleted;
    /**
     * Дата завершения задачи.
     */
    @Schema(description = "Дата завершения задачи", maxLength = DataTypes.DATE_LENGTH)
    private LocalDate completedDate;
}
