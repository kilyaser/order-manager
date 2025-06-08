package com.profcut.ordermanager.domain.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "UiOrderConstraint")
public class UiOrderConstraint {
    /**
     * Признак возможности удаления заказа.
     */
    private boolean cadDeleteOrder;
    /**
     * Признак возможности изменить заказ.
     */
    private boolean canChangeOrder;
}
