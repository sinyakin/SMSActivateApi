package com.sms_activate.rent;

import org.jetbrains.annotations.NotNull;

public enum StateRent {
    SUCCESS("Статус изменен успешно."),
    ERROR("")
    ;

    private String message;

    StateRent(@NotNull String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
