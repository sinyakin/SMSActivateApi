package com.sms_activate;

import org.jetbrains.annotations.NotNull;

public enum BadParameters {
    BAD_ACTION ("некорректное действие"),
    BAD_SERVICE("некорректное наименование сервиса"),
    BAD_KEY("неверный API-ключ")
    ;

    private final String message;

    BadParameters(@NotNull String message) {
        this.message = message;
    }

    public final @NotNull String getMessage() {
        return message;
    }
}
