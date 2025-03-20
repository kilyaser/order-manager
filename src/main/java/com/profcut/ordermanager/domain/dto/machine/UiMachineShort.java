package com.profcut.ordermanager.domain.dto.machine;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.enums.MachineType;
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
@Schema(description = "UiMachine")
public class UiMachineShort {
    /**
     * Идентификатор станка с ЧПУ.
     */
    @Schema(description = "Идентификатор станка с ЧПУ.", maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;
    /**
     * Тип станка с ЧПУ.
     */
    @Schema(description = "Тип станка с ЧПУ.", requiredMode = Schema.RequiredMode.REQUIRED)
    private MachineType machineType;
    /**
     * Наименование станка с ЧПУ.
     */
    @Schema(description = "Наименование станка с ЧПУ.", maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}
