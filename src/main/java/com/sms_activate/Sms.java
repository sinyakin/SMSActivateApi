package com.sms_activate;

import com.sms_activate.phone.Phone;
import org.jetbrains.annotations.NotNull;

public class Sms {
    private Phone phoneFrom;
    private String text;
    private String date;

    public Sms(@NotNull Phone phoneFrom, @NotNull String text, @NotNull String date) {
        this.phoneFrom = phoneFrom;
        this.text = text;
        this.date = date;
    }
}
