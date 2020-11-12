package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum StateActivationResponse {
  WAIT_CODE("STATUS_WAIT_CODE", "Ожидание смс.", "Waiting sms."),
  WAIT_RETRY("STATUS_WAIT_RETRY", "Ожидание уточнения кода.", "Waiting for code clarification."),
  WAIT_RESEND("STATUS_WAIT_RESEND", "Ожидание повторной отправки смс.", "Waiting for re-sending SMS."),
  CANCEL("STATUS_CANCEL", "Активация отменена.", "Activation canceled."),
  OK("STATUS_OK", "Код получен.", "Code received."),
  UNKNOWN("UNKNOWN",  "Неизвестный статус активации.", "Unknown status activation.");

  /**
   * Response from server.
   */
  private final String response;

  /**
   * Message on russian language.
   */
  private final String russianMessage;

  /**
   * Message on england language.
   */
  private final String englishMessage;

  /**
   * Code from sms.
   */
  private String codeFromSMS = null;

  /**
   * Constructor state activation with multilang.
   *
   * @param russianMessage message on russian.
   * @param englishMessage message on england.
   */
  StateActivationResponse(@NotNull String response, @NotNull String russianMessage, @NotNull String englishMessage) {
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
   * Returns the message on england.
   *
   * @return message on england.
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
   * Returns the code from sms.
   *
   * @return code from sms.
   */
  @Nullable
  public String getCodeFromSMS() {
    return codeFromSMS;
  }

  /**
   * Sets the code from sms.
   *
   * @param code code from sms.
   */
  public void setCodeFromSMS(@Nullable String code) {
    this.codeFromSMS = code;
  }

  /**
   * Returns the state activation by name.
   *
   * @param name state activation name.
   * @return state activation.
   */
  @NotNull
  public static StateActivationResponse getStateByName(@NotNull String name) {
    for (StateActivationResponse stateActivationResponse : values()) {
      if (stateActivationResponse.response.equalsIgnoreCase(name)) {
        return stateActivationResponse;
      }
    }
    return UNKNOWN;
  }
}
