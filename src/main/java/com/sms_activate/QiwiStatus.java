package com.sms_activate;

public enum QiwiStatus {
    SUCCESS(1, "оплату можно проводить"),
    FALSE(0, "прием платежей киви невозможен"),
    ;

    /**
     * Id status
     */
    private int id;

    /**
     * Description by status
     */
    private String message;

    /**
     * Constructor QiwiStatus.
     * @param id id status.
     * @param message description status.
     */
    QiwiStatus(int id, String message) {
        this.id = id;
        this.message = message;
    }

    /**
     * Returns the id status.
     * @return id status.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the description status.
     * @return the description status.
     */
    public String getMessage() {
        return message;
    }
}
