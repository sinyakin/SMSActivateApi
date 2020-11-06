package com.sms_activate.phone;

import com.sms_activate.service.Service;
import org.jetbrains.annotations.NotNull;

public class PhoneRent extends Phone {
    /**
     * End date of rent
     */
    private String endDate;

    /**
     * Constructor of phone rent.
     * @param number number phone (not be null).
     * @param id id operation.
     * @param forward forward phone number (not be null).
     * @param service service for phone number (not be null).
     * @param endDate end date of rent (not be null).
     */
    public PhoneRent(
            @NotNull String number,
            int id,
            boolean forward,
            @NotNull Service service,
            @NotNull String endDate
    ) {
        super(number, id, forward, service);
        this.endDate = endDate;
    }

    /**
     * Returns the end date of rent.
     * @return end date of rent.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of rent.
     * @param endDate end date of rent
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
