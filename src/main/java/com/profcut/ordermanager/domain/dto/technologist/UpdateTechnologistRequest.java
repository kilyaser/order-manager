package com.profcut.ordermanager.domain.dto.technologist;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "UpdateTechnologistRequest")
public class UpdateTechnologistRequest {
    /**
     * id технолога.
     */
    @Schema(description = "id технолога", maxLength = DataTypes.UUID_LENGTH)
    private UUID id;
    /**
     * Изменяемые атрибуты технолога.
     */
    @Valid
    @NotNull
    @Schema(description = "Обновленные атрибуты технолога")
    private TechnologistFieldsPatch patch;
}
