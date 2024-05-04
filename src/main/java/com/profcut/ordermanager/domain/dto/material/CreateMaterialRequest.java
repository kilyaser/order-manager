package com.profcut.ordermanager.domain.dto.material;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CreateMaterialRequest")
public class CreateMaterialRequest {
    /**
     * Тип материала.
     */
    @NotBlank
    @Schema(description = "Тип материала.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String materialType;
}
