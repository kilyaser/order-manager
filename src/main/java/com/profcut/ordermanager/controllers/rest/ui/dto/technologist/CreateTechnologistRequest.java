package com.profcut.ordermanager.controllers.rest.ui.dto.technologist;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CreateTechnologistRequest")
public class CreateTechnologistRequest {

    @Schema(description = "Ф.И.О. технололга",
    maxLength = DataTypes.STRING_LENGTH_MAX)
    private String fullName;

    @Schema(description = "e-mail",
    maxLength = DataTypes.STRING_LENGTH_MAX)
    private String email;

    @Schema(description = "Контактный телефон",
    maxLength = DataTypes.STRING_LENGTH_MAX)
    private String phone;
}
