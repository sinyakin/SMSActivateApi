package com.sms_activate.rent;

import org.jetbrains.annotations.NotNull;

public enum StatusRent {
    FINISH(1, "Финиш."),
    CANCEL(2, "Отмена."),
    ;

    private final int id;
    private String message;

    StatusRent(int id, @NotNull String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(@NotNull String message) {
        this.message = message;
    }

    public void setErrorMessage(@NotNull StateRent message) {
        this.setMessage(message.getMessage());
    }
}
