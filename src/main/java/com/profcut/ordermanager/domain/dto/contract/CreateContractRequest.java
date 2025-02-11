package com.profcut.ordermanager.domain.dto.contract;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.enums.ContractState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "CreateContractRequest")
public class CreateContractRequest {
    /**
     * Номер договора.
     */
    @NotBlank
    @Schema(description = "Номер договора", maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String contractNumber;
    /**
     * Дата договора.
     */
    @Schema(description = "Дата договора", maxLength = DataTypes.DATE_LENGTH,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate contractDate;
    /**
     * Дата, с которой договор вступает в силу.
     */
    @Schema(description = "Дата, с которой договор вступает в сил", maxLength = DataTypes.DATE_LENGTH,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate startDate;
    /**
     * Дата окончания действия договора.
     */
    @Schema(description = "Дата окончания действия договора", maxLength = DataTypes.DATE_LENGTH,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate endDate;
    /**
     * Статус договора.
     */
    @NotNull
    @Schema(description = "Статус договора", maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private ContractState status;
    /**
     * Идентификатор контрагента.
     */
    @NotNull
    @Schema(description = "Идентификатор контрагента", maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID counterpartyId;
    /**
     * Примечания.
     */
    @Schema(description = "Примечания к договору", maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String notes;
}
