package com.sms_activate.arch.activation.get_status;

import org.jetbrains.annotations.NotNull;

public enum SMSActivateGetStatus {
  WAIT_CODE("STATUS_WAIT_CODE", "Ожидание смс.", "Waiting sms."),
  WAIT_RETRY("STATUS_WAIT_RETRY", "Ожидание уточнения кода.", "Waiting for code clarification."),
  WAIT_RESEND("STATUS_WAIT_RESEND", "Ожидание повторной отправки смс.", "Waiting for re-sending SMS."),
  CANCEL("STATUS_CANCEL", "Активация отменена.", "Activation canceled."),
  OK("STATUS_OK", "Код получен.", "Code received."),
  UNKNOWN("UNKNOWN",  "Неизвестный статус активации.", "Unknown status activation.")
  ;

  /**
   * Response from server.
   */
  private final String response;

  /**
   * Message on russian language.
   */
  private final String russianMessage;

  /**
   * Message on english language.
   */
  private final String englishMessage;

  /**
   * Constructor status for method getStatus with multilang.
   *
   * @param russianMessage message on russian.
   * @param englishMessage message on english.
   */
  SMSActivateGetStatus(@NotNull String response, @NotNull String russianMessage, @NotNull String englishMessage) {
    this.russianMessage = russianMessage;
    this.englishMessage = englishMessage;
    this.response = response;
  }

  /**
   * Returns the message on russian.
   *
   * @return message on russian.
   */
  @NotNull
  public String getRussianMessage() {
    return russianMessage;
  }

  /**
   * Returns the message on english.
   *
   * @return message on english.
   */
  @NotNull
  public String getEnglishMessage() {
    return englishMessage;
  }

  /**
   * Returns the single concat messages.
   *
   * @return single concat messages.
   */
  @NotNull
  public String getMessage() {
    return String.join("/", englishMessage, russianMessage);
  }

  /**
   * Returns the state activation by name.
   *
   * @param name state activation name.
   * @return state activation.
   */
  @NotNull
  public static SMSActivateGetStatus getStatusByName(@NotNull String name) {
    name = name.toUpperCase();

    for (SMSActivateGetStatus stateActivationResponse : values()) {
      if (stateActivationResponse.response.equals(name)) {
        return stateActivationResponse;
      }
    }

    return UNKNOWN;
  }
}