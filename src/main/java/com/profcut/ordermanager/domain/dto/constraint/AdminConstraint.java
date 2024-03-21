package com.profcut.ordermanager.domain.dto.constraint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AdminConstraint")
public class AdminConstraint {

    @Schema(description = "Признак, что можно получить список доспуных ролей.")
    private boolean isAllowViewRoles;

    @Schema(description = "Признак доступа к панели администрацтора.")
    private boolean isAllowViewAdminConsole;
}
