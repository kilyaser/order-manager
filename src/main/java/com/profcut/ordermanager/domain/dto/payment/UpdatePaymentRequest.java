package com.profcut.ordermanager.domain.dto.payment;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "UpdatePaymentRequest")
public class UpdatePaymentRequest {
    /**
     * Идентификатор платежа.
     */
    @NotNull
    @Schema(description = "Идентификатор платежа.",
            maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID paymentId;
    /**
     * Обновленные атрибуты платежа.
     */
    @NotNull
    private PaymentFieldsPatch patch;
}
