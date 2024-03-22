package com.profcut.ordermanager.domain.dto.product;

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
@Schema(description = "UiProduct")
public class UiProduct {
    /**
     * Идентификатор изделия.
     */
    @Schema(description = "id изделия", maxLength = DataTypes.UUID_LENGTH)
    private UUID productId;
    /**
     * Наименование изделия.
     */
    @Schema(description = "Наименование изделия", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String productName;
}
