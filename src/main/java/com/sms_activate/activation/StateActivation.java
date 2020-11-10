package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum StateActivation {
    STATUS_WAIT_CODE("Ожидание смс.", "Waiting sms."),
    STATUS_WAIT_RETRY("Ожидание уточнения кода.", "Waiting for code clarification."),
    STATUS_WAIT_RESEND("Ожидание повторной отправки смс.", "Waiting for re-sending SMS."),
    STATUS_CANCEL("Активация отменена.", "Activation canceled."),
    STATUS_OK("Код получен.", "Code received."),
    UNKNOWN("Неизвестно.", "Unknown.")
    ;

    /**
     * Message on russian language.
     */
    private final String russianMessage;

    /**
     * Message on england language.
     */
    private final String englandMessage;

    /**
     * Code from sms.
     */
    private String code;

    /**
     * Constructor state activation with multilang.
     * @param russianMessage message on russian.
     * @param englandMessage message on england.
     */
    StateActivation(@NotNull String russianMessage, @NotNull String englandMessage) {
        this.russianMessage = russianMessage;
        this.englandMessage = englandMessage;
    }

    /**
     * Returns the message on russian.
     * @return message on russian.
     */
    @NotNull
    public String getRussianMessage() {
        return russianMessage;
    }

    /**
     * Returns the message on england.
     * @return message on england.
     */
    @NotNull
    public String getEnglandMessage() {
        return englandMessage;
    }

    /**
     * Returns the single concat messages.
     * @return single concat messages.
     */
    @NotNull
    public String getMessage() {
        return String.join("/", englandMessage, russianMessage);
    }

    /**
     * Returns the code from sms.
     * @return code from sms.
     */
    @Nullable
    //todo
    public String getCode() {
        return code;
    }

    /**
     * Sets the code from sms.
     * @param code code from sms.
     */
    //todo
    public void setCode(@Nullable String code) {
        this.code = code;
    }

    /**
     * Returns the state activation by name.
     * @param name state activation name.
     * @return state activation.
     */
    @NotNull
    //todo
    public static StateActivation getStateByName(@NotNull String name) {
        try {
            return StateActivation.valueOf(StateActivation.class, name);
        } catch (IllegalArgumentException e) {
            return StateActivation.UNKNOWN;
        }
    }
}
