package com.sms_activate.error.rent;

import org.jetbrains.annotations.NotNull;

public enum ErrorRent {
  ALREADY_FINISH("ALREADY_FINISH", "Аренда уже завершена.", "Rent has been finish."),
  ALREADY_CANCEL("ALREADY_CANCEL", "Аренда уже отменена.", "Rent has been cancel."),
  NO_ID_RENT("NO_ID_RENT", "Не указан id Аренды.", "Not specified id rent."),
  INCORECT_STATUS("INCORECT_STATUS", "Отсутствует или неправильно указан статус.", "Missing or incorrect status."),
  CANT_CANCEL("CANT_CANCEL", "Невозможно отменить аренду (более 20 мин.).", "It is impossible to cancel the lease (more than 20 minutes)."),
  INVALID_PHONE("INVALID_PHONE", "Номер арендован не вами (неправильный id аренды).", ""),
  SQL_ERROR("SQL_ERROR", "Ошибка sql сервера.", "Error sql server."),
  NO_NUMBERS("NO_NUMBERS", "Арендованных номеров нет.", "There are no rented numbers."),
  ACCOUNT_INACTIVE("ACCOUNT_INACTIVE", "Свободных номеров нет.", "There are no vacant numbers."),
  NO_BALANCE("NO_BALANCE", "Нет денег на счету.", "Balance in your account is zero."),
  UNKNOWN("UNKNOWN", "Неизвестная ошибка аренды.", "Unknown rent error.");

  /**
   * Response from server
   */
  private final String response;

  /**
   * Message error on russian language.
   */
  private final String russianMessage;

  /**
   * Message error on england language.
   */
  private final String englishMessage;

  /**
   * Constructor state error with multilang.
   *
   * @param russianMessage error message on russian.
   * @param englishMessage error message on england.
   */
  ErrorRent(@NotNull String response, @NotNull String russianMessage, @NotNull String englishMessage) {
    this.russianMessage = russianMessage;
    this.englishMessage = englishMessage;
    this.response = response;
  }

  /**
   * Returns the response from server.
   *
   * @return response from server.
   */
  @NotNull
  protected String getResponse() {
    return this.response;
  }

  /**
   * Returns the error message on russian.
   *
   * @return error message on russian.
   */
  @NotNull
  public String getRussianMessage() {
    return russianMessage;
  }

  /**
   * Returns the error message on england.
   *
   * @return error message on england.
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
   * Returns the state error by name.
   *
   * @param name error name.
   * @return state error.
   */
  @NotNull
  public static ErrorRent getStateByName(@NotNull String name) {
    for (ErrorRent errorRent : values()) {
      if (errorRent.getResponse().equalsIgnoreCase(name)) {
        return errorRent;
      }
    }

    return UNKNOWN;
  }
}
