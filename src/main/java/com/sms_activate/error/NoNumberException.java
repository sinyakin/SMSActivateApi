package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class NoNumberException extends Exception {
    public NoNumberException(@NotNull String message) {
        super(message);
    }
}
