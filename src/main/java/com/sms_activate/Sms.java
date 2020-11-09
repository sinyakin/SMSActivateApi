package com.sms_activate;

import com.sms_activate.phone.Phone;
import org.jetbrains.annotations.NotNull;

public class Sms {
    /**
     * Phone where the sms came from.
     */
    private final Phone phoneFrom;

    /**
     * Text sms.
     */
    private final String text;

    /**
     * SMS arrival date.
     */
    private final String date;

    /**
     * Constructor sms with phone, text, date.
     * @param phoneFrom phone where the sms came from (not be null).
     * @param text text sms (not be null).
     * @param date sms arrival date (not be null).
     */
    public Sms(@NotNull Phone phoneFrom, @NotNull String text, @NotNull String date) {
        this.phoneFrom = phoneFrom;
        this.text = text;
        this.date = date;
    }

    /**
     * Returns the phone where the sms came from.
     * @return phone where the sms came from.
     */
    public Phone getPhoneFrom() {
        return phoneFrom;
    }

    /**
     * Returns the text sms.
     * @return text sms.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the sms arrival date.
     * @return sms arrival date.
     */
    public String getDate() {
        return date;
    }
}
