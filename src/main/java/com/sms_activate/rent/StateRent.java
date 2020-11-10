package com.sms_activate.rent;

import org.jetbrains.annotations.NotNull;

public enum StateRent {
  //todo
  SUCCESS("Статус изменен успешно.", "Status has been changed."),
  ERROR("Ошибка.", "Error."),
  UNKNOWN("Неизвестно.", "Unknown.");

  /**
   * Message on russian language.
   */
  private final String russianMessage;

  /**
   * Message on england language.
   */
  private final String englandMessage;

  /**
   * Constructor state with messages.
   *
   * @param russianMessage message on russian language.
   * @param englandMessage message on england language
   */
  StateRent(@NotNull String russianMessage, @NotNull String englandMessage) {
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
    return String.join("/", englandMessage, russianMessage);
  }

  /**
   * Returns the state by name.
   *
   * @param name state name.
   * @return state.
   */
  @NotNull
  public static StateRent getStateRentByName(@NotNull String name) {
    try {
      return StateRent.valueOf(StateRent.class, name);
    } catch (IllegalArgumentException e) {
      return StateRent.UNKNOWN;
    }
  }
}
