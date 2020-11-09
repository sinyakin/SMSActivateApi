package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

public enum StatusActivation {
    SEND_READY_NUMBER(1, "Сообщить о готовности номера (смс на номер отправлено).", "Inform about the readiness of the number (SMS sent to the number)."),
    REQUEST_ONE_MORE_CODE(3, "Запросить еще один код (бесплатно).", "Request another code (free)."),
    FINISH(6, "Завершить активацию.", "Finish the activation."),
    CANCEL(8, "Сообщить о том, что номер использован и отменить активацию", "Report that the number has been used and cancel activation."),
    ;

    /**
     * Special id status
     */
    private final int id;

    /**
     * Message on russian language.
     */
    private final String russianMessage;

    /**
     * Message on england language.
     */
    private final String englandMessage;

    /**
     * Constructor status activation.
     * @param id special id status.
     * @param russianMessage description status on russian language.
     * @param englandMessage description status on england language.
     */
    StatusActivation(int id, @NotNull String russianMessage, @NotNull String englandMessage) {
       this.id = id;
       this.russianMessage = russianMessage;
       this.englandMessage = englandMessage;
    }

    /**
     * Returns the message on russian.
     * @return message on russian.
     */
    public String getRussianMessage() {
        return russianMessage;
    }

    /**
     * Returns the message on england.
     * @return message on england.
     */
    public String getEnglandMessage() {
        return englandMessage;
    }

    /**
     * Returns the single concat messages.
     * @return single concat messages.
     */
    public String getMessage() {
        return String.join("/", englandMessage, russianMessage);
    }

    /**
     * Return the id status
     * @return id status
     */
    public int getId() {
        return id;
    }
}
