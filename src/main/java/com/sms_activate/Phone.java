package com.sms_activate;

import org.jetbrains.annotations.NotNull;

public class Phone {
    /**
     * Phone number
     */
    private final String number;

    /**
     * Id phone
     */
    private final  String id;

    /**
     * Forward phone number
     */
    private final boolean forward;

    public Phone(@NotNull String number, int id, boolean forward) {
        this(number, id + "", forward);
    }

    /**
     * Constructor Phone with number, id, forward
     * @param number number phone (not be null)
     * @param id id operation
     * @param forward forward phone number (not be null)
     */
    public Phone(@NotNull String number, @NotNull String id, boolean forward) {
        this.number = number;
        this.id = id;
        this.forward = forward;
    }

    /**
     * Returns the phone number.
     * @return phone number (not be null).
     */
    public String getNumber() {
        return number;
    }

    /**
     * Returns the id operation.
     * @return id operation.
     */
    public  String getId() {
        return id;
    }

    /**
     * Returns the phone is forward.
     * @return phone is forward.
     */
    public boolean isForward() {
        return forward;
    }
}
