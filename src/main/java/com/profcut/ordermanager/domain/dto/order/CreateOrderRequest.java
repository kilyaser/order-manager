package com.profcut.ordermanager.domain.dto.order;

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
     * Ссылка на рабочую директорию с документами по заказу.
     */
    @Schema(description = "Ссылка на рабочую директорию", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String workFolderLink;
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
    @Schema(description = "Признак государственного заказа")
    private boolean isGovernmentOrder;
    /**
     *  Id контрагента заказчик.
     */
    @NotNull
    @Schema(description = "Id контрагента заказчик", maxLength = DataTypes.UUID_LENGTH)
    private UUID counterpartyId;
    /**
     * Список позиций заказа.
     */
    @ArraySchema(schema = @Schema(description = "Список позиций заказа.",
            maxLength = DataTypes.ARRAY_MAX_ITEMS_VALUE))
    private List<OrderItemRequest> orderItemRequest;
}
