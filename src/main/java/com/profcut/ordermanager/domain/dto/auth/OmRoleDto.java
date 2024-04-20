package com.profcut.ordermanager.domain.dto.auth;


import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmRoleDto {

    @Schema(description = "роль пользователя", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String role;
}
