package com.profcut.ordermanager.domain.dto.auth;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmUserFieldPatch {

    @NotBlank
    @Schema(description = "Имя пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String firstName;

    @Schema(description = "Фамилия пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String lastName;

    @Schema(description = "Отчество пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String patronymic;

    @Email(message = "Wrong email")
    @Schema(description = "e-mail пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String email;

    @Pattern(regexp = "^\\d{11}$", message = "phone number doesn't match the template")
    @Schema(description = "Контактный телефон пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String phone;

    @Schema(description = "Дата рождения пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String birthday;
}
