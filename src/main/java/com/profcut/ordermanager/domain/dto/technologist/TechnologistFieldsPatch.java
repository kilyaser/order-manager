package com.profcut.ordermanager.domain.dto.technologist;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 2, max = 50)
    @Schema(description = "Имя технолога", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String firstName;
    /**
     * Фамилия технолога.
     */
    @Size(min = 3, max = 50)
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
    @Pattern(regexp = "^\\+\\d{5,15}$", message = "Wrong phone number")
    @Schema(description = "Телефон технолога", maxLength = DataTypes.STRING_LENGTH_MAX)
    private String phone;
}
