package com.sms_activate.qiwi;

import org.jetbrains.annotations.NotNull;

public enum SMSActivateQiwiStatus {
  UNKNOWN("UNKNOWN", "", ""),
  SUCCESS("SUCCESS", "Оплату можно проводить.", "Payment can be made."),
  FALSE("FALSE", "Прием платежей киви невозможен.", "Acceptance of qiwi payments is not possible."),
  ;

  /**
   * Response status from server.
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
   * Constructor QiwiStatus.
   *
   * @param russianMessage message on russian language.
   * @param englishMessage message on english language.
   */
  SMSActivateQiwiStatus(@NotNull String response, @NotNull String russianMessage, @NotNull String englishMessage) {
    this.response = response;
    this.russianMessage = russianMessage;
    this.englishMessage = englishMessage;
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
   * Returns status by name or unknown if not contains.
   * @param name status name.
   * @return status or unknown if not contains.
   */
  @NotNull
  public static SMSActivateQiwiStatus getStatusByName(@NotNull String name) {
    name = name.toUpperCase();

    for (SMSActivateQiwiStatus status : values()) {
      if (status.response.equals(name)) {
        return status;
      }
    }

    return UNKNOWN;
  }
}
