package com.sms_activate.rent;

import org.jetbrains.annotations.NotNull;

public enum StatusRentNumber {
  SUCCESS("Успешно", "Success"),
  ERROR("Ошибка.", "Error"),
  UNKNOWN("Неизвестно", "Unknown");

  /**
   * Message on russian language.
   */
  private final String russianMessage;

  /**
   * Message on england language.
   */
  private final String englandMessage;

  /**
   * Constructor AccessStatusActivation with multilang.
   *
   * @param russianMessage message on russian.
   * @param englandMessage message on england.
   */
  StatusRentNumber(@NotNull String russianMessage, @NotNull String englandMessage) {
    this.russianMessage = russianMessage;
    this.englandMessage = englandMessage;
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
    return String.join(" / ", englandMessage, russianMessage);
  }

  /**
   * Returns the state by name.
   *
   * @param name state name.
   * @return state.
   */
  @NotNull
  public static StatusRentNumber getStatusByName(@NotNull String name) {
    try {
      return StatusRentNumber.valueOf(StatusRentNumber.class, name);
    } catch (IllegalArgumentException e) {
      return StatusRentNumber.UNKNOWN;
    }
  }
}
