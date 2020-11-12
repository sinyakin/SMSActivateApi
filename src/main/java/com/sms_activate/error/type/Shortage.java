package com.sms_activate.error.type;

import org.jetbrains.annotations.NotNull;

/**
 * This type of error occurs in the event of a lack of funds, numbers, or something else.
 */
public enum Shortage {
  NO_NUMBERS("NO_NUMBERS", "Номеров нет.", "There are no numbers."),
  NO_BALANCE("NO_BALANCE", "Нет денег на счету.", "Balance in your account is zero."),
  ACCOUNT_INACTIVE("ACCOUNT_INACTIVE", "Свободных номеров нет.", "There are no vacant numbers."),
  UNKNOWN("", "", "");

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
   * Constructor Shortage with messages.
   *
   * @param response       response from server.
   * @param russianMessage message on russian language.
   * @param englishMessage message on english language.
   */
  Shortage(@NotNull String response, @NotNull String russianMessage, @NotNull String englishMessage) {
    this.response = response;
    this.russianMessage = russianMessage;
    this.englishMessage = englishMessage;
  }

  /**
   * Returns the message on russian language.
   *
   * @return message on russian language.
   */
  @NotNull
  public String getRussianMessage() {
    return russianMessage;
  }

  /**
   * Returns the message on english language.
   *
   * @return message on english language.
   */
  @NotNull
  public String getEnglishMessage() {
    return englishMessage;
  }

  /**
   * Returns the error by name.
   *
   * @param name name error in response from server.
   * @return error.
   */
  @NotNull
  public static Shortage getErrorByName(@NotNull String name) {
    name = name.toUpperCase();

    for (Shortage shortage : values()) {
      if (shortage.response.equals(name)) {
        return shortage;
      }
    }

    return UNKNOWN;
  }
}
