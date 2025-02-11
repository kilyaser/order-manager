package com.profcut.ordermanager.domain.dto.contract;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "UiContracts")
public class UiContracts {
    /**
     * Список контрактов.
     */
    @ArraySchema(schema = @Schema(description = "Список контрактов",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<UiContract> contracts;
}
