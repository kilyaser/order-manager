package com.profcut.ordermanager.domain.dto.auth;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Schema(description = "id пользователя", maxLength = DataTypes.UUID_LENGTH)
    private UUID id;
    /**
     * Изменяемые поля.
     */
    @Valid
    @NotNull
    @Schema(description = "Изменяемые поля")
    private OmUserFieldPatch patch;
}
