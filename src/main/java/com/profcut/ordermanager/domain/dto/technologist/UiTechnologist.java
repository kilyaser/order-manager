package com.profcut.ordermanager.domain.dto.technologist;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@Schema(name = "UiTechnologist")
@Accessors(chain = true)
public class UiTechnologist {

    /**
     * id технолога.
     */
    @Schema(description = "id технолога", maxLength = DataTypes.UUID_LENGTH)
    private UUID id;
    /**
     * Имя технолога.
     */
    @Schema(description = "Имя технолога", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String firstName;
    /**
     * Фамилия технолога.
     */
    @Schema(description = "Фамилия технолога", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String lastName;
    /**
     * Отчество технолога.
     */
    @Schema(description = "Отчество технолога", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String patronymic;
    /**
     * e-mail технолога.
     */
    @Schema(description = "e-mail технолога", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String email;
    /**
     * Контактный телефон технолога.
     */
    @Schema(description = "Контактный телефон", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String phone;
}
