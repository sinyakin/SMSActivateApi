
package com.sms_activate.qiwi;

import org.jetbrains.annotations.NotNull;

public enum QiwiStatus {
  SUCCESS(1, "Оплату можно проводить.", "Payment can be made."),
  FALSE(0, "Прием платежей киви невозможен.", "Acceptance of qiwi payments is not possible."),;

  /**
   * Id status
   */
  private final int id;

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
   * @param id             id status.
   * @param russianMessage message on russian language.
   * @param englishMessage message on english language.
   */
  QiwiStatus(int id, @NotNull String russianMessage, @NotNull String englishMessage) {
    this.id = id;
    this.russianMessage = russianMessage;
    this.englishMessage = englishMessage;
  }

  /**
   * Returns the id status.
   *
   * @return id status.
   */
  public int getId() {
    return id;
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
  public static QiwiStatus getStatusByName(@NotNull String name) {
    return QiwiStatus.valueOf(QiwiStatus.class, name);
  }
}
