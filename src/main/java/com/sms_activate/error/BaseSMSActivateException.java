package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class BaseSMSActivateException extends Exception {
    /**
     * Message on england language.
     */
    private final String englandMessage;

    /**
     * Message on russian language.
     */
    private final String russianMessage;

    /**
     * Constructor base sms activate exception with sublang.
     * @param englandMessage message on england language.
     * @param russianMessage message on russian language.
     */
    public BaseSMSActivateException(@NotNull String englandMessage, @NotNull String russianMessage) {
        super(englandMessage);

        this.englandMessage = englandMessage;
        this.russianMessage = russianMessage;
    }

    /**
     * Returns the message on england language.
     * @return message on england language.
     */
    public String getEnglandMessage() {
        return englandMessage;
    }

    /**
     * Returns the message on russian language.
     * @return message on russian language.
     */
    public String getRussianMessage() {
        return russianMessage;
    }

    /**
     * Returns the concat messages.
     * @return concat messages.
     */
    @Override
    public String getMessage() {
        return String.join("/", englandMessage, russianMessage);
    }
}
