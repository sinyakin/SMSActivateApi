package com.sms_activate.rent;

import org.jetbrains.annotations.NotNull;

public enum StateRentResponse {
  SUCCESS("Статус изменен успешно.", "Status has been changed."),
  ERROR("Ошибка.", "Error.");

  /**
   * Message on russian language.
   */
  private final String russianMessage;

  /**
   * Message on england language.
   */
  private final String englishMessage;

  /**
   * Constructor state with messages.
   *
   * @param russianMessage message on russian language.
   * @param englishMessage message on england language
   */
  StateRentResponse(@NotNull String russianMessage, @NotNull String englishMessage) {
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
  public static StateRentResponse getStateRentByName(@NotNull String name) {
    return StateRentResponse.valueOf(StateRentResponse.class, name);
  }
}
