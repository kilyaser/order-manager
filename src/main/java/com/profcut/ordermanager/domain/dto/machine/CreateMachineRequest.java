package com.profcut.ordermanager.domain.dto.machine;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.enums.MachineType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "CreateMachineRequest")
public class CreateMachineRequest {
    /**
     * Тип станка с ЧПУ.
     */
    @NotNull
    @Schema(description = "Тип станка с ЧПУ.")
    private MachineType machineType;
    /**
     * Наименование станка с ЧПУ.
     */
    @NotNull
    @Schema(description = "Наименование станка с ЧПУ.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String name;
}
