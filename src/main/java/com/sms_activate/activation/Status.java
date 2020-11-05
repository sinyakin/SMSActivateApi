package com.sms_activate.activation;

public enum Status {
    SEND_READY_NUMBER(1, "Сообщить о готовности номера (смс на номер отправлено)."),
    REQUEST_ONE_MORE_CODE(3, "Запросить еще один код (бесплатно)."),
    FINISH(6, "Завершить активацию."),
    CANCEL(8, "Сообщить о том, что номер использован и отменить активацию"),
    ;

    /**
     * Special id status
     */
    private final int id;

    /**
     * Description status
     */
    private final String message;

    /**
     * Constructor status activation
     * @param id special id status
     * @param message description status
     */
    Status(int id, String message) {
       this.id = id;
       this.message = message;
    }

    /**
     * Return the description status
     * @return description status
     */
    public String getMessage() {
        return message;
    }

    /**
     * Return the id status
     * @return id status
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "StatusActivation { " +
                "id=" + id +
                ", message='" + message + '\'' +
                " }";
    }
}
