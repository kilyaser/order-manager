package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.enums.OrderState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "UiOrderShort")
public class UiOrderShort {
    /**
     * Идентификор заказа.
     */
    @Schema(description = "id заказа.", maxLength = DataTypes.UUID_LENGTH, requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID orderId;
    /**
     * Номер заказа.
     */
    @Schema(description = "Номер заказа.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String orderNumber;
    /**
     * Сумма заказа действующая c НДС.
     */
    @Schema(description = "Сумма заказа с НДС.",
            minimum = DataTypes.MINIMUM_VALUE,
            maximum = DataTypes.MAXIMUM_LONG_VALUE)
    private BigDecimal currentSum;
    /**
     * Сумма заказа действующая без НДС.
     */
    @Schema(description = "Сумма заказа без НДС.",
            minimum = DataTypes.MINIMUM_VALUE,
            maximum = DataTypes.MAXIMUM_LONG_VALUE)
    private BigDecimal totalPrice;
    /**
     * Статус заказа.
     */
    @Schema(description = "Статус заказа.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private OrderState orderState;
    /**
     *  Дата создания заказа.
     */
    @Schema(description = "Дата создания заказа.")
    private LocalDate createdDate;
    /**
     * Дата завершеня заказа.
     */
    @Schema(description = "Дата завершеня заказа.",
            minLength = DataTypes.DATE_TIME_LENGTH,
            maxLength = DataTypes.DATE_TIME_LENGTH)
    private LocalDate completionDate;
    /**
     * Признак государственного заказа.
     */
    @Schema(description = "Признак государственного заказа")
    private boolean isGovernmentOrder;
    /**
     * Наименование контрагента.
     */
    @Schema(description = "Наименование контрагента")
    private String counterpartyName;
}
