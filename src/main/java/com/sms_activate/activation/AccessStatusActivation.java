package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

public enum AccessStatusActivation {
  READY("ACCESS_READY", "Готовность номера подтверждена.", "Number readiness confirmed."),
  RETRY_GET("ACCESS_RETRY_GET", "Ожидание нового смс.", "Wait new sms."),
  ACTIVATION("ACCESS_ACTIVATION", "Сервис успешно активирован.", "The service has been successfully activated."),
  CANCEL("ACCESS_CANCEL", "Активация была отменена.", "Activation has been canceled."),
  UNKNOWN("UNKNOWN", "Неизвестный статус активации.", "Unknown status activation.");

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
   * Constructor AccessStatusActivation with multilang.
   *
   * @param russianMessage message on russian.
   * @param englishMessage message on england.
   * @param response response from server.
   */
  AccessStatusActivation(@NotNull String response, @NotNull String russianMessage, @NotNull String englishMessage) {
    this.russianMessage = russianMessage;
    this.englishMessage = englishMessage;
    this.response = response;
  }

  /**
   * Returns the message on russian.
   *
   * @return on russian.
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
   * Returns the state by name.
   *
   * @param name state name.
   * @return state.
   */
  @NotNull
  public static AccessStatusActivation getStatusByName(@NotNull String name) {
    for (AccessStatusActivation accessStatusActivation : values()) {
      if (accessStatusActivation.response.equalsIgnoreCase(name)) {
        return accessStatusActivation;
      }
    }

    return UNKNOWN;
  }
}
