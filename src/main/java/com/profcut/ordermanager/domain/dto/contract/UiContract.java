package com.profcut.ordermanager.domain.dto.contract;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.dto.counterparty.UiCounterpartyShort;
import com.profcut.ordermanager.domain.enums.ContractState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "UiContract")
public class UiContract {
    /**
     * Идентификатор контракта.
     */
    @Schema(description = "Идентификатор контракта", maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID contractId;
    /**
     * Номер договора.
     */
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
    @Schema(description = "Дата, с которой договор вступает в силу", maxLength = DataTypes.DATE_LENGTH,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate startDate;
    /**
     * Дата окончания действия договора.
     */
    @Schema(description = "Дата окончания действия договора", maxLength = DataTypes.DATE_LENGTH,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate endDate;
    /**
     * Контрагент - заказчик.
     */
    @Schema(description = "Контрагент", requiredMode = Schema.RequiredMode.REQUIRED)
    private UiCounterpartyShort counterparty;
    /**
     * Статус договора.
     */
    @Schema(description = "Статус договора", maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private ContractState status;
    /**
     * Примечания.
     */
    @Schema(description = "Примечания", maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String notes;
}
