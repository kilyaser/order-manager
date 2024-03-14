package com.profcut.ordermanager.domain.dto.technologist;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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

    @Schema(description = "Имя технололга", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @Schema(description = "Фамилия технолога", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Schema(description = "Отчество технолога", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String patronymic;

    @Email
    @Schema(description = "e-mail", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Контактный телефон", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String phone;
}
