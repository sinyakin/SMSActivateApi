package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class SQLServerException extends Exception {
    public SQLServerException() {
        this("Ошибка SQL-сервера");
    }

    public SQLServerException(@NotNull String message) {
        super(message);
    }
}
