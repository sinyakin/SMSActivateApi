package com.sms_activate;

import org.jetbrains.annotations.NotNull;

public class Phone {
    /**
     * Phone number
     */
    private String number;

    /**
     * Id phone
     */
    private int id;

    /**
     * Forward phone number
     */
    private boolean forward;

    /**
     * Constructor Phone with number, id, forward
     * @param number number phone (not be null)
     * @param id id operation
     * @param forward forward phone number (not be null)
     */
    public Phone(@NotNull String number, int id, boolean forward) {
        this.number = number;
        this.id = id;
        this.forward = forward;
    }

    /**
     * Returns phone number
     * @return phone number
     */
    public String getNumber() {
        return number;
    }

    /**
     *
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }
}
