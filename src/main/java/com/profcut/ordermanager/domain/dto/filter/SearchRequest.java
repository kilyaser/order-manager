package com.profcut.ordermanager.domain.dto.filter;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "SearchRequest")
public class SearchRequest {

    /**
     * Строка поиска.
     */
    @NotBlank
    @Schema(description = "Строка поиска", maxLength = DataTypes.STRING_LENGTH_MAX)
    public String search;
}
