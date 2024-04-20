package com.profcut.ordermanager.domain.dto.auth;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateRequest {

    @Schema(description = "Старый пароль пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;

    @Pattern(regexp = "^(?=.{7,20}$)[0-9a-zA-Z]*$",
            message = "new password doesn't match the template")
    @Schema(description = "Новый пароль пользователя", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}
