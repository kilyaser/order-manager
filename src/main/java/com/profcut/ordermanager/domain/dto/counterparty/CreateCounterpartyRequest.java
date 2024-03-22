package com.profcut.ordermanager.domain.dto.counterparty;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CreateCounterpartyRequest")
public class CreateCounterpartyRequest {
    /**
     * Полное наименование контрагента.
     */
    @Schema(description = "Полное наименование контрагента",
            maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String fullName;
    /**
     * Краткое наименование контрагента.
     */
    @NotBlank
    @Schema(description = "Краткое наименование контрагента",
            maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    /**
     * ИНН контрагента.
     */
    @Schema(description = "ИНН контрагента",
            maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String inn;
    /**
     * е-mail контрагента.
     */
    @Email
    @Schema(description = "е-mail контрагента", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String email;
    /**
     * Контактный телефон контрагента.
     */
    @Pattern(regexp = "^\\+\\d{5,15}$", message = "phone number doesn't match the template")
    @Schema(description = "Телефон контрагента", maxLength = DataTypes.STRING_LENGTH_MAX, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String phone;
}
