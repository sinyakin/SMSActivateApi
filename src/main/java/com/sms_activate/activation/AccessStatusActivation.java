package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

public enum AccessStatusActivation {
    ACCESS_READY("Готовность номера подтверждена.", "Number readiness confirmed."),
    ACCESS_RETRY_GET("Ожидание нового смс.", "Wait new sms."),
    ACCESS_ACTIVATION("Сервис успешно активирован.", "The service has been successfully activated."),
    ACCESS_CANCEL("Активация отменена.", "Activation has been canceled."),
    ;

    /**
     * Message on russian language.
     */
    private final String russianMessage;

    /**
     * Message on england language.
     */
    private final String englandMessage;

    /**
     * Constructor AccessStatusActivation with multilang.
     * @param russianMessage message on russian.
     * @param englandMessage message on england.
     */
    AccessStatusActivation(@NotNull String russianMessage, @NotNull String englandMessage) {
        this.russianMessage = russianMessage;
        this.englandMessage = englandMessage;
    }

    /**
     * Returns the message on russian.
     * @return on russian.
     */
    public String getRussianMessage() {
        return russianMessage;
    }

    /**
     * Returns the message on england.
     * @return message on england.
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
