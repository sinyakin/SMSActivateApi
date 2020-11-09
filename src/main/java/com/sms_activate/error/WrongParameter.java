package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public enum WrongParameter {
    BAD_ACTION("Некорректное действие.", "Wrong action."),
    BAD_SERVICE("Некорректное наименование сервиса.", "Wrong name service."),
    BAD_KEY("Неверный API-ключ.", "Wrong api-key."),
    BAD_STATUS("Попытка установить несуществующий статус.", "An attempt to establish a non-existent status."),
    WRONG_OPERATOR("Некорректный оператор.", "Wrong operator"),
    WRONG_EXCEPTION_PHONE("Некорректные исключающие префиксы.", "Wrong exception prefix."),
    WRONG_SERVICE("Некорректные сервисы.", "Wrong services.")
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
