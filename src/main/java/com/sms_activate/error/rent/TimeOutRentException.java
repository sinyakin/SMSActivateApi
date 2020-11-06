package com.sms_activate.error.rent;

public class TimeOutRentException extends RentException {
    public TimeOutRentException() {
        super("Невозможно отменить аренду (более 20 мин.).");
    }
}
