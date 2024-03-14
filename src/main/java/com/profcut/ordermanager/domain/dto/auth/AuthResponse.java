package com.profcut.ordermanager.domain.dto.auth;

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
public class AuthResponse {

    @Schema(description = "Токен доступа", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String accessToken;

    @Schema(description = "Токен обновления", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String refreshToken;
}
