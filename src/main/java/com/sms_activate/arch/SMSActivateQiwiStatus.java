package com.sms_activate.arch;

import org.jetbrains.annotations.NotNull;

public enum SMSActivateQiwiStatus {
  SUCCESS("Оплату можно проводить.", "Payment can be made."),
  FALSE("Прием платежей киви невозможен.", "Acceptance of qiwi payments is not possible."),
  ;

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
  SMSActivateQiwiStatus(@NotNull String russianMessage, @NotNull String englishMessage) {
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

  @NotNull
  public static SMSActivateQiwiStatus getStatusByName(@NotNull String name) {
    return SMSActivateQiwiStatus.valueOf(SMSActivateQiwiStatus.class, name);
  }
}
