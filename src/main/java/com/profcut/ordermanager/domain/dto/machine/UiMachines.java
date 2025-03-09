package com.profcut.ordermanager.domain.dto.machine;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "UiMachines")
public class UiMachines {

    /**
     * Перечень оборудования
     */
    @Schema(description = "Перечень оборудования")
    @ArraySchema(schema = @Schema(description = "Оборудование",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<UiMachine> machines;
}
