package com.profcut.ordermanager.domain.dto.counterparty;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "UiCounterpartyShort")
public class UiCounterpartyShort {
    /**
     * Идентификор контрагента.
     */
    @Schema(description = "id контрагента", maxLength = DataTypes.UUID_LENGTH)
    private UUID id;
    /**
     * ИНН Контрагента.
     */
    @Schema(description = "ИНН Контрагента", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String inn;
    /**
     * Краткое наименование контрагента.
     */
    @Schema(description = "Краткое наименование контрагента", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String name;
}
