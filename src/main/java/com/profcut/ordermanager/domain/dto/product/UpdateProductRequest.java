package com.profcut.ordermanager.domain.dto.product;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "UpdateProductRequest")
public class UpdateProductRequest {

    /**
     * Идентификатор изделия.
     */
    @NotNull
    @Schema(description = "id изделия", maxLength = DataTypes.UUID_LENGTH)
    private UUID productId;
    /**
     * Изменяемые атрибуты продукты.
     */
    @Valid
    @NotNull
    @Schema(description = "Обновленные атрибуты продукта")
    private ProductFieldsPatch patch;
}
