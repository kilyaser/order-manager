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
@Schema(description = "CreateProductRequest")
public class CreateProductRequest {
    /**
     * Наименование изделия
     */
    @NotNull
    @Schema(description = "Наименование изделия", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String productName;
}
