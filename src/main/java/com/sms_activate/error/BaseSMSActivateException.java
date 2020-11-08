package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class BaseSMSActivateException extends Exception {
    private final String englandMessage;
    private final String russianMessage;

    public BaseSMSActivateException(@NotNull String englandMessage, @NotNull String russianMessage) {
        super(englandMessage);

        this.englandMessage = englandMessage;
        this.russianMessage = russianMessage;
    }

    public String getEnglandMessage() {
        return englandMessage;
    }

    public String getRussianMessage() {
        return russianMessage;
    }

    @Override
    public String getMessage() {
        return String.join("/", englandMessage, russianMessage);
    }
}
