package com.profcut.ordermanager.domain.dto.filter;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ProductFilter")
public class FilterRequest {
    /**
     * Строка поиска.
     */
    @Schema(description = "Строка поиска", maxLength = DataTypes.STRING_LENGTH_MAX)
    public String search;
    /**
     * Параметры страницы запроса.
     */
    @Schema(description = "Параметры страницы запроса")
    public PageRequest pageRequest;
}
