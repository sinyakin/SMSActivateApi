package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class RepeatAdditionalException extends Exception {
    public RepeatAdditionalException(@NotNull String message) {
        super(message);
    }
}
