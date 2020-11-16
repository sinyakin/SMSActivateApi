package com.sms_activate.old.rent;

import org.jetbrains.annotations.NotNull;

public enum StatusRentRequest {
  FINISH(1, "Завершить аренду.", "Finish rent."),
  CANCEL(2, "Отменить аренду.", "Cancel rent."),
  ;

  /**
   * Id status.
   */
  private final int id;

  /**
   * Message status on russian language.
   */
  private final String russianMessage;

  /**
   * Message status on english language.
   */
  private final String englishMessage;

  /**
   * Constructor with id and multilang.
   *
   * @param id             is status.
   * @param russianMessage message on russian lang.
   * @param englishMessage message on english lang.
   */
  StatusRentRequest(int id, @NotNull String russianMessage, @NotNull String englishMessage) {
    this.id = id;
    this.russianMessage = russianMessage;
    this.englishMessage = englishMessage;
  }

  /**
   * Returns the status id.
   *
   * @return status id.
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
}
