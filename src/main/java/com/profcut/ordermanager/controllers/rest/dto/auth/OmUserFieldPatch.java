package com.profcut.ordermanager.controllers.rest.dto.auth;

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
public class OmUserFieldPatch {


    @Schema(description = "Имя пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String firstName;

    @Schema(description = "Фамилия пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String lastName;

    @Schema(description = "Отчество пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String patronymic;

    @Schema(description = "e-mail пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String email;

    @Schema(description = "Контактный телефон пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String phone;

    @Schema(description = "Дата рождения пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String birthday;
}
