package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class BannedException extends BaseSMSActivateException {
    public BannedException(@NotNull String englandMessage, @NotNull String russianMessage) {
        super(englandMessage, russianMessage);
    }
}
