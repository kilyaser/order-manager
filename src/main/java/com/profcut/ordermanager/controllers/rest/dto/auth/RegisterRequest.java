package com.profcut.ordermanager.controllers.rest.dto.auth;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "RegisterRequest")
public class RegisterRequest {

    /**
     * Имя пользователя.
     */
    @Schema(description = "Имя пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;
    /**
     * Фамилия пользователя.
     */
    @Schema(description = "Фамилия пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;
    /**
     * Отчество пользователя.
     */
    @Schema(description = "Отчество пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String patronymic;
    /**
     * e-mail/логин пользователя.
     */
    @Schema(description = "email/логин пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    /**
     * Телефон пользователя.
     */
    @Schema(description = "Телефон пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String phone;
    /**
     * Пароль пользователя.
     */
    @Schema(description = "Пароль пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    /**
     * Дата рождения пользователя.
     */
    @Schema(description = "Дата рождения пользователя", maxLength = DataTypes.DATE_LENGTH, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Date birthday;
    /**
     * Список ролей пользователя.
     */
    @ArraySchema(schema = @Schema(description = "Список ролей", maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE, requiredMode = Schema.RequiredMode.NOT_REQUIRED))
    private Set<OmRole> roles;

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + "******" + '\'' +
                ", birthday=" + birthday +
                ", roles=" + roles +
                '}';
    }
}
