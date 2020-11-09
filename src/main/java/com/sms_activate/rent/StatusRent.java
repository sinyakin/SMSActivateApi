package com.sms_activate.rent;

import org.jetbrains.annotations.NotNull;

public enum StatusRent {
  FINISH(1, "Финиш.", "Finish."),
  CANCEL(2, "Отмена.", "Cancel."),
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
   * Message status on england language.
   */
  private final String englandMessage;

  /**
   * Constructor with id and multilang.
   *
   * @param id             is status.
   * @param russianMessage message on russian lang.
   * @param englandMessage message on england lang.
   */
  StatusRent(int id, @NotNull String russianMessage, @NotNull String englandMessage) {
    this.id = id;
    this.russianMessage = russianMessage;
    this.englandMessage = englandMessage;
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
}
