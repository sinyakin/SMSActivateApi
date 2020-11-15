package com.sms_activate.error.type;

import org.jetbrains.annotations.NotNull;

public enum RentError {
  ALREADY_FINISH("ALREADY_FINISH", "Аренда уже завершена.", "Rent has been finished."),
  ALREADY_CANCEL("ALREADY_CANCEL", "Аренда уже отменена.", "Rent has been canceled."),
  STATUS_FINISH("STATUS_FINISH", "Аренда оплачна и завершена.", "Rent has been payed and finished."),
  STATUS_CANCEL("STATUS_CANCEL", "Аренда отменена с возвратом денег.", "Rent canceled with a refund."),
  STATUS_WAIT_CODE("STATUS_WAIT_CODE", "Ожидание первой смс.", "Waiting first sms."),
  NO_ID_RENT("NO_ID_RENT", "Не указан id Аренды.", "Not specified id rent."),
  CANT_CANCEL("CANT_CANCEL", "Невозможно отменить аренду (более 20 мин.).", "It is impossible to cancel the lease (more than 20 minutes)."),
  INVALID_PHONE("INVALID_PHONE", "Номер арендован не вами (неправильный id аренды).", "The room was not rented by you (wrong rental id)."),
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
   * Message error on english language.
   */
  private final String englishMessage;

  /**
   * Constructor state error with multilang.
   *
   * @param russianMessage error message on russian.
   * @param englishMessage error message on english.
   */
  RentError(@NotNull String response, @NotNull String russianMessage, @NotNull String englishMessage) {
    this.russianMessage = russianMessage;
    this.englishMessage = englishMessage;
    this.response = response;
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
   * Returns the error message on english.
   *
   * @return error message on english.
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
  public static RentError getErroByName(@NotNull String name) {
    name = name.toUpperCase();

    for (RentError rentError : values()) {
      if (rentError.response.equals(name)) {
        return rentError;
      }
    }

    return UNKNOWN;
  }
}
