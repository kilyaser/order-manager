package com.profcut.ordermanager.domain.dto.order;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderFieldsPatch {
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
     * Дата завершеня заказа.
     */
    @Schema(description = "Дата завершеня заказа.",
            minLength = DataTypes.DATE_TIME_LENGTH,
            maxLength = DataTypes.DATE_TIME_LENGTH)
    private LocalDateTime completionDate;
    /**
     * Признак государственного заказа.
     */
    @Schema(description = "Признак государственного заказа")
    private Boolean isGovernmentOrder;
    /**
     * Признак влкючения НДС в стоимость.
     */
    @Schema(description = "Признак влкючения НДС в стоимость.")
    private Boolean isVatInclude;
}
