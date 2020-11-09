package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class IncorrectStatusException extends BaseSMSActivateException {
    /**
     * Constructor base sms activate exception with sublang.
     * @param englandMessage message on england language.
     * @param russianMessage message on russian language.
     */
    public IncorrectStatusException(@NotNull String englandMessage, @NotNull String russianMessage) {
        super(englandMessage, russianMessage);
    }
}
