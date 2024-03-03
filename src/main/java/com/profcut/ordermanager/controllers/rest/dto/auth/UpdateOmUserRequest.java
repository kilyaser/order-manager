package com.profcut.ordermanager.controllers.rest.dto.auth;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "UpdateOmUserRequest")
public class UpdateOmUserRequest {

    /**
     * id пользователя.
     */
    @Schema(description = "id пользователя", maxLength = DataTypes.UUID_LENGTH)
    private UUID id;
    /**
     * Изменяемые поля.
     */
    @Schema(description = "Изменяемые поля")
    private OmUserFieldPatch patch;
}
