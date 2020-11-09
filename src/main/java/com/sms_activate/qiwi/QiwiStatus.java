package com.sms_activate.qiwi;

import org.jetbrains.annotations.NotNull;

public enum QiwiStatus {
  SUCCESS(1, "Оплату можно проводить.", "Payment can be made."),
  FALSE(0, "Прием платежей киви невозможен.", "Acceptance of qiwi payments is not possible."),
  UNKNOWN(-1, "Неизвестно", "Unknown.");

  /**
   * Id status
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
   * Constructor QiwiStatus.
   *
   * @param id             id status.
   * @param russianMessage message on russian language.
   * @param englandMessage message on england language.
   */
  QiwiStatus(int id, @NotNull String russianMessage, @NotNull String englandMessage) {
    this.id = id;
    this.russianMessage = russianMessage;
    this.englandMessage = englandMessage;
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
   * Returns the message on england.
   *
   * @return message on england.
   */
  @NotNull
  public String getEnglandMessage() {
    return englandMessage;
  }

  /**
   * Returns the single concat messages.
   *
   * @return single concat messages.
   */
  @NotNull
  public String getMessage() {
    return String.join("/", englandMessage, russianMessage);
  }

  @NotNull
  public static QiwiStatus getStatusByName(@NotNull String name) {
    try {
      return QiwiStatus.valueOf(QiwiStatus.class, name);
    } catch (IllegalArgumentException e) {
      return QiwiStatus.UNKNOWN;
    }
  }
}
