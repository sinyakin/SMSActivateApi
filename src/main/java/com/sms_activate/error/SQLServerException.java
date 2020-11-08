package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class SQLServerException extends BaseSMSActivateException {
    public SQLServerException(@NotNull String englandMessage, @NotNull String russianMessage) {
        super(englandMessage, russianMessage);
    }
}
