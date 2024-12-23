package com.profcut.ordermanager.domain.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "PaymentFieldsPatch")
public class PaymentFieldsPatch {
    /**
     * Размер платежа.
     */
    @Schema(description = "Сумма платежа.")
    private BigDecimal paymentSum;
    /**
     * Дата платежа
     */
    @Schema(description = "Дата платежа.")
    private LocalDate paymentDate;
}
