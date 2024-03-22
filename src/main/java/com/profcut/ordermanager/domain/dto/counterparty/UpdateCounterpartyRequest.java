package com.profcut.ordermanager.domain.dto.counterparty;

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
@Schema(name = "UpdateCounterpartyRequest")
public class UpdateCounterpartyRequest {
    /**
     * ID контрагента.
     */
    @NotNull
    @Schema(description = "id контрагента", maxLength = DataTypes.UUID_LENGTH)
    private UUID id;

    @Valid
    @NotNull
    @Schema(description = "Обновленные атрибуты контрагента")
    private CounterpartyFieldsPatch patch;
}
