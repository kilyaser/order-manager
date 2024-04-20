package com.profcut.ordermanager.domain.exceptions;

public class UpdateOmUserException extends RuntimeException {
    public UpdateOmUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public static UpdateOmUserException byBirthday(String birthday, Exception ex) {
        return new UpdateOmUserException("Ошибка обновления пользователя, некорректно указана дата рождения: %s".formatted(birthday), ex);
    }
}
