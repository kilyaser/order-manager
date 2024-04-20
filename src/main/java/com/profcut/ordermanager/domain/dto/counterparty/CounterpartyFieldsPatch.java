package com.profcut.ordermanager.domain.dto.counterparty;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounterpartyFieldsPatch {
    /**
     * Полное наименование контрагента.
     */
    @Schema(description = "Полное наименование контрагента",
            maxLength = DataTypes.STRING_LENGTH_MAX)
    private String fullName;
    /**
     * Краткое наименование контрагента.
     */
    @Schema(description = "Краткое наименование контрагента",
            maxLength = DataTypes.STRING_LENGTH_MAX)
    private String name;
    /**
     * ИНН контрагента.
     */
    @Schema(description = "ИНН контрагента",
            maxLength = DataTypes.STRING_LENGTH_MAX)
    private String inn;
    /**
     * е-mail контрагента.
     */
    @Email
    @Schema(description = "е-mail контрагента",
            maxLength = DataTypes.STRING_LENGTH_MAX)
    private String email;
    /**
     * Контактный телефон контрагента.
     */
    @Pattern(regexp = "^\\+\\d{5,15}$", message = "phone number doesn't match the template")
    @Schema(description = "Контактный телефон контрагента",
            maxLength = DataTypes.STRING_LENGTH_MAX)
    private String phone;
}
