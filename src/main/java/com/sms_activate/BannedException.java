package com.sms_activate;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDate;

public class BannedException extends Exception {
    private LocalDate date;

    public BannedException(@NotNull String message) {
        super(message);
    }

    public BannedException(@NotNull String message, @NotNull String dateFormat) {
        super(message);
        this.date = LocalDate.from(Instant.parse(dateFormat));
    }

    public BannedException(@NotNull String message, @NotNull LocalDate date) {
        super(message);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ": " + date;
    }
}
