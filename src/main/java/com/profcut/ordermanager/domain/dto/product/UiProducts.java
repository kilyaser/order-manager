package com.profcut.ordermanager.domain.dto.product;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "UiProducts")
public class UiProducts {
    /**
     * Наименование изделия.
     */
    @ArraySchema(schema = @Schema(description = "Change access modifier",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<UiProduct> products;
}
