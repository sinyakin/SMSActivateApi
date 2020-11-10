package com.sms_activate.rent;

import org.jetbrains.annotations.NotNull;

public enum StatusRentNumberResponse {
  SUCCESS("Успешно", "Success."),
  ERROR("Ошибка.", "Error."),
  UNKNOWN("Неизвестный статуст аренды.", "Unknown status rent.");

  /**
   * Message on russian language.
   */
  private final String russianMessage;

  /**
   * Message on england language.
   */
  private final String englishMessage;

  /**
   * Constructor AccessStatusActivation with multilang.
   *
   * @param russianMessage message on russian.
   * @param englishMessage message on england.
   */
  StatusRentNumberResponse(@NotNull String russianMessage, @NotNull String englishMessage) {
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
    return String.join(" / ", englishMessage, russianMessage);
  }

  /**
   * Returns the state by name.
   *
   * @param name state name.
   * @return state.
   */
  @NotNull
  public static StatusRentNumberResponse getStatusByName(@NotNull String name) {
    try {
      return StatusRentNumberResponse.valueOf(StatusRentNumberResponse.class, name);
    } catch (IllegalArgumentException e) {
      return StatusRentNumberResponse.UNKNOWN;
    }
  }
}
