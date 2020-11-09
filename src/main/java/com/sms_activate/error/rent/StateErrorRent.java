package com.sms_activate.error.rent;

import org.jetbrains.annotations.NotNull;

public enum StateErrorRent {
  ALREADY_FINISH("Аренда уже завершена.", "Rent has been finish."),
  ALREADY_CANCEL("Аренда уже отменена.", "Rent has been cancel."),
  NO_ID_RENT("Не указан id Аренды.", "Not specified id rent."),
  INCORECT_STATUS("Отсутствует или неправильно указан статус.", "Missing or incorrect status."),
  CANT_CANCEL("Невозможно отменить аренду (более 20 мин.).", "It is impossible to cancel the lease (more than 20 minutes)."),
  INVALID_PHONE("Номер арендован не вами (неправильный id аренды).", ""),
  SQL_ERROR("Ошибка sql сервера.", "Error sql server."),
  NO_NUMBERS("Арендованных номеров нет.", "There are no rented numbers."),
  ACCOUNT_INACTIVE("Свободных номеров нет.", "There are no vacant numbers."),
  UNKNOWN("Неизвестно.", "Unknown.");

  /**
   * Message error on russian language.
   */
  private final String russianMessage;

  /**
   * Message error on england language.
   */
  private final String englandMessage;

  /**
   * Constructor state error with multilang.
   *
   * @param russianMessage error message on russian.
   * @param englandMessage error message on england.
   */
  StateErrorRent(@NotNull String russianMessage, @NotNull String englandMessage) {
    this.russianMessage = russianMessage;
    this.englandMessage = englandMessage;
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
   * Returns the state error by name.
   *
   * @param name error name.
   * @return state error.
   */
  @NotNull
  public static StateErrorRent getStateByName(@NotNull String name) {
    try {
      return StateErrorRent.valueOf(StateErrorRent.class, name);
    } catch (IllegalArgumentException e) {
      return StateErrorRent.UNKNOWN;
    }
  }
}
