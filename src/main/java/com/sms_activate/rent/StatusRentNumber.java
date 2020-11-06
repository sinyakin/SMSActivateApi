package com.sms_activate.rent;

public enum StatusRentNumber {
    SUCCESS("Успешно"),
    ERROR("")
    ;

    private final String message;

    StatusRentNumber(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
