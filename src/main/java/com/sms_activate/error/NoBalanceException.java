package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class NoBalanceException extends Exception {
    public NoBalanceException() {
        this("Нет денег на счету.");
    }

    public NoBalanceException(@NotNull String message) {
        super(message);
    }
}
