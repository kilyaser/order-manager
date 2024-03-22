package com.profcut.ordermanager.domain.dto.auth;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolesResponse {
    /**
     * Роли, доступные для назначения.
     */
    @Schema(description = "Роли, доступные для назначения", maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE)
    Set<String> availableRoles;
}
