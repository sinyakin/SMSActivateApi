package com.sms_activate.activation;

public enum AccessStatusActivation {
    ACCESS_READY("Готовность номера подтверждена."),
    ACCESS_RETRY_GET("Ожидание нового смс."),
    ACCESS_ACTIVATION("Сервис успешно активирован."),
    ACCESS_CANCEL("Активация отменена."),
    ;

    private final String message;

    AccessStatusActivation(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}