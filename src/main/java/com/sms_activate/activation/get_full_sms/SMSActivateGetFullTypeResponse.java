package com.sms_activate.activation.get_full_sms;

import org.jetbrains.annotations.NotNull;

public enum SMSActivateGetFullTypeResponse {
  WAIT_CODE("STATUS_WAIT_CODE", "Ожидание смс.", "Waiting sms."),
  CANCEL("STATUS_CANCEL", "Активация отменена.", "Activation canceled."),
  FULL_SMS("FULL_SMS", "Полный текст смс.", "Full text sms."),
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
   * Message on english language.
   */
  private final String englishMessage;

  /**
   * Constructor status for method getStatus with multilang.
   *
   * @param russianMessage message on russian.
   * @param englishMessage message on english.
   */
  SMSActivateGetFullTypeResponse(@NotNull String response, @NotNull String russianMessage, @NotNull String englishMessage) {
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
   * Returns the response from server.
   *
   * @return response from server.
   */
  @NotNull
  public String getResponse() {
    return response;
  }

  /**
   * Returns the state activation by name.
   *
   * @param name state activation name.
   * @return state activation.
   */
  @NotNull
  public static SMSActivateGetFullTypeResponse getStatusByName(@NotNull String name) {
    name = name.toUpperCase();

    for (SMSActivateGetFullTypeResponse type : values()) {
      if (type.getResponse().contains(name)) {
        return type;
      }
    }

    return UNKNOWN;
  }
}
