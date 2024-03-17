package com.profcut.ordermanager.domain.dto.technologist;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Size(min = 2, max = 50)
    @Schema(description = "Имя технололга", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @Size(min = 3, max = 50)
    @Schema(description = "Фамилия технолога", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Schema(description = "Отчество технолога", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String patronymic;

    @Email
    @Schema(description = "e-mail", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Pattern(regexp = "^\\+\\d{5,15}$", message = "Wrong phone number")
    @Schema(description = "Контактный телефон", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String phone;
}
