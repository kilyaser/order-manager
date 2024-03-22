package com.profcut.ordermanager.domain.dto.material;

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
@Schema(description = "UiMaterial")
public class UiMaterial {
    /**
     * Идентификатор материала.
     */
    @Schema(description = "id материала.", maxLength = DataTypes.UUID_LENGTH)
    private UUID id;
    /**
     * Тип материала.
     */
    @Schema(description = "Тип материала.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String materialType;
}
