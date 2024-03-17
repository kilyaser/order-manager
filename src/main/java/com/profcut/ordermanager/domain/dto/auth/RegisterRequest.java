package com.profcut.ordermanager.domain.dto.auth;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 2, max = 50)
    @Schema(description = "Имя пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;
    /**
     * Фамилия пользователя.
     */
    @Size(min = 3, max = 50)
    @Schema(description = "Фамилия пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;
    /**
     * Отчество пользователя.
     */
    @Schema(description = "Отчество пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String patronymic;
    /**
     * e-mail/логин пользователя.
     */
    @Email
    @Schema(description = "email/логин пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    /**
     * Телефон пользователя.
     */
    @Pattern(regexp = "^\\+\\d{5,15}$", message = "phone number doesn't match the template")
    @Schema(description = "Телефон пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String phone;
    /**
     * Пароль пользователя.
     */
    @Pattern(regexp = "^(?=.{7,20}$)[0-9a-zA-Z]*$",
            message = "new password doesn't match the template")
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
