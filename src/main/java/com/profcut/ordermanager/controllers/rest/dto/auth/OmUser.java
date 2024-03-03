package com.profcut.ordermanager.controllers.rest.dto.auth;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmUser {

    /**
     * ID пользователя.
     */
    @Schema(description = "id пользователя", maxLength = DataTypes.UUID_LENGTH)
    private UUID id;
    /**
     * Имя пользователя.
     */
    @Schema(description = "Имя пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String firstName;
    /**
     * Фамилия пользователя.
     */
    @Schema(description = "Фамилия пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String lastName;
    /**
     * Отчество пользователя.
     */
    @Schema(description = "Отчество пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String patronymic;
    /**
     * Дата рождения пользователя.
     */
    @Schema(description = "Дата рождения пользователя", maxLength = DataTypes.DATE_LENGTH, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String birthday;
    /**
     * e-mail/логин пользователя.
     */
    @Email
    @Schema(description = "email/логин пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    /**
     * Телефон пользователя.
     */
    @Pattern(regexp = "^\\d{11}$", message = "phone number doesn't match the template")
    @Schema(description = "Телефон пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String phone;
    /**
     * Список ролей пользователя.
     */
    @ArraySchema(schema = @Schema(description = "Список ролей", maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE, requiredMode = Schema.RequiredMode.NOT_REQUIRED))
    private Set<String> roles;
}
