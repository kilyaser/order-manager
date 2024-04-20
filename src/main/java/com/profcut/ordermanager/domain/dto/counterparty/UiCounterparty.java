package com.profcut.ordermanager.domain.dto.counterparty;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

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
}
