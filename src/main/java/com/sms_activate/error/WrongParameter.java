package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public enum WrongParameter {
    BAD_ACTION("Некорректное действие.", ""),
    BAD_SERVICE("Некорректное наименование сервиса.", ""),
    BAD_KEY("Неверный API-ключ.", ""),
    BAD_STATUS("Попытка установить несущетвующий статус.", ""),
    WRONG_OPERATOR("Некорректный оператор.", ""),
    WRONG_EXCEPTION_PHONE("Некорректные исключающие префиксы.", ""),
    WRONG_SERVICE("Некорректные сервисы.", "")
    ;

    private final String russianMessage;
    private final String englandMessage;

    WrongParameter(@NotNull String russianMessage, @NotNull String englandMessage) {
        this.russianMessage = russianMessage;
        this.englandMessage = englandMessage;
    }

    public String getRussianMessage() {
        return russianMessage;
    }

    public String getEnglandMessage() {
        return englandMessage;
    }

    public String getMessage() {
        return String.join("/", englandMessage, russianMessage);
    }
}
