package com.profcut.ordermanager.domain.dto.payment;

import com.profcut.ordermanager.domain.dto.counterparty.UiCounterpartyShort;
import com.profcut.ordermanager.domain.dto.order.UiOrderShort;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "UiPayment")
@EqualsAndHashCode(callSuper = true)
public class UiPayment extends UiPaymentShort {
    /**
     *  Контрагент заказчик.
     */
    @Schema(description = "Контрагент заказчик.")
    private UiCounterpartyShort counterparty;
    /**
     * Краткая информация о заказе.
     */
    @Schema(description = "Краткая информация о заказе.")
    private UiOrderShort order;
    /**
     * Дата последнего изменения.
     */
    @Schema(description = "Дата последнего изменения.")
    private LocalDate modifiedDate;
}
