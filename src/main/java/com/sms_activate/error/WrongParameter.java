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

    /**
     * Message error on russian language.
     */
    private final String russianMessage;

    /**
     * Message error on england language.
     */
    private final String englandMessage;

    /**
     * Constructor WrongParameter with multilang.
     * @param russianMessage error message on russian.
     * @param englandMessage error message on england.
     */
    WrongParameter(@NotNull String russianMessage, @NotNull String englandMessage) {
        this.russianMessage = russianMessage;
        this.englandMessage = englandMessage;
    }

    /**
     * Returns the error message on russian.
     * @return error message on russian.
     */
    public String getRussianMessage() {
        return russianMessage;
    }

    /**
     * Returns the error message on england.
     * @return error message on england.
     */
    public String getEnglandMessage() {
        return englandMessage;
    }

    /**
     * Returns the single concat messages.
     * @return single concat messages.
     */
    public String getMessage() {
        return String.join("/", englandMessage, russianMessage);
    }
}
