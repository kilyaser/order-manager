package com.profcut.ordermanager.domain.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CreateOrderRequest")
public class CreateOrderRequest {

    /**
     * Наименование заказа.
     */
    @Schema(description = "Наименование заказа",
            maxLength = DataTypes.STRING_LENGTH_MAX,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String orderName;
    /**
     * Дата завершеня заказа.
     */
    @Schema(description = "Плановая дата завершения",
            minLength = DataTypes.DATE_TIME_LENGTH,
            maxLength = DataTypes.DATE_TIME_LENGTH,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime completionDate;
    /**
     * Признак государственного заказа.
     */
    @JsonProperty("isGovernmentOrder")
    @Schema(description = "Признак государственного заказа", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean isGovernmentOrder;
    /**
     * Признак государственного заказа.
     */
    @JsonProperty("isVatInclude")
    @Schema(description = "Признак включения НДС в стоимсть")
    private boolean isVatInclude;
    /**
     * Id контрагента заказчик.
     */
    @NotNull
    @Schema(description = "Id контрагента заказчик", maxLength = DataTypes.UUID_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID counterpartyId;
    /**
     * Id договора.
     */
    @Schema(description = "Id договора", maxLength = DataTypes.UUID_LENGTH)
    private UUID contractId;
    /**
     * Список позиций заказа.
     */
    @ArraySchema(schema = @Schema(description = "Список позиций заказа.",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<OrderItemRequest> itemRequests;
}
