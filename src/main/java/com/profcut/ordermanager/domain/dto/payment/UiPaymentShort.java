package com.profcut.ordermanager.domain.dto.payment;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "UiPaymentShort")
public class UiPaymentShort {
    /**
     * Идентификатор платежа.
     */
    @Schema(description = "id платежа.", maxLength = DataTypes.UUID_LENGTH)
    private UUID paymentId;
    /**
     * Размер платежа.
     */
    @Schema(description = "Сумма платежа.",
            minimum = DataTypes.MINIMUM_VALUE,
            maximum = DataTypes.MAXIMUM_LONG_VALUE)
    private BigDecimal paymentSum;
    /**
     * Дата платежа.
     */
    @Schema(description = "Дата платежа.",
            minLength = DataTypes.DATE_TIME_LENGTH,
            maxLength = DataTypes.DATE_TIME_LENGTH)
    private LocalDateTime paymentDate;

}
