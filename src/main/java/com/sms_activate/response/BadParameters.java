package com.sms_activate.response;

import org.jetbrains.annotations.NotNull;

public enum BadParameters {
    ;

    private final String message;

    BadParameters(@NotNull String message) {
        this.message = message;
    }

    public final @NotNull String getMessage() {
        return message;
    }
}
