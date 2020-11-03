package com.sms_activate.response;

public enum SuccessResponse {
    NO_BALANCE("Закончился баланс"),
    NO_NUMBERS("Нет номеров"),
    ACCESS_NUMBER("$id:$number , где ($id - id операции, $number - номер телефона)"),
    ACCESS_BALANCE("Баланс на счету")
    ;

    private final String message;

    SuccessResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
