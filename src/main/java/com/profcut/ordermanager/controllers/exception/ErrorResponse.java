package com.profcut.ordermanager.controllers.exception;

import com.profcut.ordermanager.common.consts.DataTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@Schema(description = "Ответ с ошибкой")
public class ErrorResponse {

    @Schema(description = "Код ошибки", maximum = "100", minimum = "511" )
    private int code;

    @Schema(description = "Класс исключения", minLength = 1, maxLength = 255)
    private String exClass;

    @Schema(description = "Класс исключения-причины", minLength = 1, maxLength = 255)
    private String causeClass;

    @Schema(description = "Сообщение об ошибке", minLength = 1, maxLength = 255)
    private String message;

    @Schema(description = "Сообщение об ошибке-причине", minLength = 1, maxLength = 255)
    private String causeMessage;

    public void setMessage(String message) {
        this.message = StringUtils.abbreviate(message, DataTypes.STRING_LENGTH_MAX);
    }

    public void setCauseMessage(String causeMessage) {
        this.causeMessage = StringUtils.abbreviate(causeMessage, DataTypes.STRING_LENGTH_MAX);
    }
}
