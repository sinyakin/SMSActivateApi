package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class BannedException extends Exception {
    public BannedException(@NotNull String message) {
        super(message);
    }
}
