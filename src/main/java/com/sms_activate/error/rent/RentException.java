package com.sms_activate.error.rent;

import org.jetbrains.annotations.NotNull;

public class RentException extends Exception {
    public RentException() {
        this("Не указан id Аренды");
    }

    public RentException(@NotNull String message) {
        super(message);
    }
}
