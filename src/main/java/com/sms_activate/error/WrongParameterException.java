package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class WrongParameterException extends Exception {
    public WrongParameterException(@NotNull String message) {
        super(message);
    }
}
