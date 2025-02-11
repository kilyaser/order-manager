package com.profcut.ordermanager.domain.dto.counterparty;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.dto.contract.UiContract;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "UiCounterparty")
public class UiCounterparty extends UiCounterpartyShort {
    /**
     * Полное наименование контрагента.
     */
    @Schema(description = "Полное наименование контрагента.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String fullName;
    /**
     * е-mail контрагента.
     */
    @Schema(description = "е-mail контрагента.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String email;
    /**
     * Контактный телефон контрагента.
     */
    @Schema(description = "Контактный телефон контрагента.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String phone;
    /**
     * Список контрактов.
     */
    @ArraySchema(schema = @Schema(description = "Список контрактов",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<UiContract> contracts;
}
