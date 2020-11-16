package com.sms_activate.rent;

import org.jetbrains.annotations.NotNull;

public enum StateRentResponse {
  SUCCESS("SUCCESS", "Статус изменен успешно.", "Status has been changed."),
  ERROR("ERROR", "Ошибка.", "Error."),
  UNKNOWN("", "Неизвестный статуст аренды.", "Unknown status rent.");

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
   * Constructor state with messages.
   *
   * @param russianMessage message on russian language.
   * @param englishMessage message on english language
   */
  StateRentResponse(@NotNull String response, @NotNull String russianMessage, @NotNull String englishMessage) {
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
   * Returns the state by name.
   *
   * @param name state name.
   * @return state.
   */
  @NotNull
  public static StateRentResponse getStateRentByName(@NotNull String name) {
    name = name.toUpperCase();

    for (StateRentResponse stateRentResponse : values()) {
      if (stateRentResponse.response.equals(name)) {
        return stateRentResponse;
      }
    }

    return UNKNOWN;
  }
}
