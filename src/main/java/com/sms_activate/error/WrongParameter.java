package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public enum WrongParameter {
    BAD_ACTION ("Некорректное действие."),
    BAD_SERVICE("Некорректное наименование сервиса."),
    BAD_KEY("Неверный API-ключ."),
    BAD_STATUS("Попытка установить несущетвующий статус."),
    WRONG_OPERATOR("Некорректный оператор."),
    WRONG_EXCEPTION_PHONE("Некорректные исключающие префиксы."),
    WRONG_SERVICE("Некорректные сервисы.")
    ;

    private final String message;

    WrongParameter(@NotNull String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
