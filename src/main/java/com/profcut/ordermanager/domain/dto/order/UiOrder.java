package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import com.profcut.ordermanager.domain.dto.counterparty.UiCounterpartyShort;
import com.profcut.ordermanager.domain.dto.payment.UiPaymentShort;
import com.profcut.ordermanager.domain.dto.task.UiTask;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "UiOrder")
public class UiOrder extends UiOrderShort {
    /**
     * Наименование заказа.
     */
    @Schema(description = "Наименование заказа.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String orderName;
    /**
     * Номер счета.
     */
    @Schema(description = "Номер счета.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String billNumber;
    /**
     * Ссылка на рабочую папку.
     */
    @Schema(description = "Ссылка на рабочую папку.", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String workFolderLink;
    /**
     * Признак влкючения НДС в стоимость.
     */
    @Schema(description = "Признак влкючения НДС в стоимость.", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean isVatInclude;
    /**
     * Сумма НДС.
     */
    @Schema(description = "Сумма НДС.", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal vat;
    /**
     * Сумма долга.
     */
    @Schema(description = "Сумма долга.", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal debtSum;
    /**
     * Дата последнего изменения.
     */
    @Schema(description = "Дата последнего изменения.", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate modifiedDate;
    /**
     *  Контрагент заказчик.
     */
    @Schema(description = "Контрагент заказчик.")
    private UiCounterpartyShort counterparty;
    /**
     * Логин пользователя создавшего заказ.
     */
    @Schema(description = "Логин пользователя создавшего заказ.")
    private String author;
    /**
     * Изделия относящиеся к заказу.
     */
    @ArraySchema(schema = @Schema(description = "Изделия относящиеся к заказу.",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<UiOrderItem> orderItems;
    /**
     * Платежи по заказу.
     */
    @ArraySchema(schema = @Schema(description = "Платежи по заказу.",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<UiPaymentShort> payments;
    /**
     * Задачи по заказу.
     */
    @ArraySchema(schema = @Schema(description = "Задачи по заказу.",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<UiTask> tasks;
}
