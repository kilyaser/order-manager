package com.profcut.ordermanager.controllers.rest.dto.technologist;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@Schema(name = "UiTechnologist")
@Accessors(chain = true)
public class UiTechnologist {

    @Schema(description = "id технолога", maxLength = DataTypes.UUID_LENGTH)
    private UUID id;

    @Schema(description = "Ф.И.О. технолога", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String fullName;

    @Schema(description = "e-mail технолога", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String email;

    @Schema(description = "Контактный телефон", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String phone;
}
