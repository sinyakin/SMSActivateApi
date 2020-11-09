package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

public enum StateActivation {
    STATUS_WAIT_CODE("Ожидание смс.", "Wait sms."),
    STATUS_WAIT_RETRY("Ожидание уточнения кода.", ""),
    STATUS_WAIT_RESEND("Ожидание повторной отправки смс.", ""),
    STATUS_CANCEL("Активация отменена.", ""),
    STATUS_OK("Код получен.", ""),
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
     * Code from sms.
     */
    private String code;

    /**
     * Constructor state activation with multilang.
     * @param russianMessage message on russian.
     * @param englandMessage message on england.
     */
    StateActivation(@NotNull String russianMessage, @NotNull String englandMessage) {
        this.russianMessage = russianMessage;
        this.englandMessage = englandMessage;
    }

    /**
     * Returns the message on russian.
     * @return message on russian.
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

    /**
     * Returns the code from sms.
     * @return code from sms.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code from sms.
     * @param code code from sms.
     */
    public void setCode(String code) {
        this.code = code;
    }
}
