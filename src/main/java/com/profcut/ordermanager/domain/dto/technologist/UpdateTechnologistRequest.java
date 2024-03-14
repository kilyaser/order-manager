package com.profcut.ordermanager.domain.dto.technologist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "UpdateTechnologistRequest")
public class UpdateTechnologistRequest {
    /**
     * id технолога.
     */
    private UUID id;
    /**
     * Изменяемые поля.
     */
    private TechnologistFieldsPatch patch;
}
