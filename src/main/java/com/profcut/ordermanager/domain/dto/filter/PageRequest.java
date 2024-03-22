package com.profcut.ordermanager.domain.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "PageRequest")
public class PageRequest {

    @Schema(description = "Номер страницы", minimum = "0", defaultValue = "0")
    private Integer page;
    @Schema(description = "Размер страницы", minimum = "1", defaultValue = "20")
    private Integer size;
}
