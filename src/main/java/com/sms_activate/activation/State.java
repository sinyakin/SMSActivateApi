package com.sms_activate.activation;

public enum State {
    STATUS_WAIT_CODE("Ожидание смс."),
    STATUS_WAIT_RETRY("Ожидание уточнения кода."),
    STATUS_WAIT_RESEND("Ожидание повторной отправки смс."),
    STATUS_CANCEL("Активация отменена."),
    STATUS_OK("Код получен."),
    ;

    /**
     *
     */
    private final String message;

    /**
     *
     * @param message
     */
    State(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }
}
