package com.profcut.ordermanager.domain.dto.technologist;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnologistFieldsPatch {

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
    @Email
    @Schema(description = "e-mail технолога", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String email;
    /**
     * Телефон технолога.
     */
    @Schema(description = "Телефон технолога", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String phone;
}
