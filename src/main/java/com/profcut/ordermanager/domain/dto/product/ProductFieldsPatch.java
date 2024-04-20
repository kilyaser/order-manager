package com.profcut.ordermanager.domain.dto.product;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFieldsPatch {

    /**
     * Наименование изделия.
     */
    @NotNull
    @Schema(description = "Наименование изделия", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String productName;
}
