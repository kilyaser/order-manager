package com.profcut.ordermanager.domain.dto.payment;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CreatePaymentRequest")
public class CreatePaymentRequest {
    /**
     * id контрагента.
     */
    @NotNull
    @Schema(description = "id контрагента.",
            maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID counterpartyId;
    /**
     * id заказа.
     */
    @NotNull
    @Schema(description = "id заказа.",
            maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID orderId;
    /**
     * Размер платежа.
     */
    @NotNull
    @Schema(description = "Сумма платежа.")
    private BigDecimal paymentSum;
    /**
     * Дата платежа.
     */
    @Schema(description = "Дата платежа.")
    private LocalDateTime paymentDate;
}
