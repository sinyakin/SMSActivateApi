package com.sms_activate.error.type;

import org.jetbrains.annotations.NotNull;

public enum WrongParameter {
  BAD_ACTION("BAD_ACTION", "Некорректное действие.", "Wrong action."),
  BAD_SERVICE("BAD_SERVICE", "Некорректное наименование сервиса.", "Wrong name service."),
  BAD_KEY("BAD_KEY", "Неверный API-ключ.", "Wrong api-key."),
  BAD_STATUS("BAD_STATUS", "Попытка установить несуществующий статус.", "An attempt to establish a non-existent status."),
  WRONG_OPERATOR("WRONG_OPERATOR", "Некорректный оператор.", "Wrong operator"),
  WRONG_EXCEPTION_PHONE("WRONG_EXCEPTION_PHONE", "Некорректные исключающие префиксы.", "Wrong exception prefix."),
  WRONG_SERVICE("WRONG_SERVICE", "Некорректные сервисы.", "Wrong services."),
  WRONG_ACTIVATION_ID("WRONG_ACTIVATION_ID", "Неверный ID родительской активации.", "Wrong ID parent activation."),
  WRONG_SECURITY("WRONG_SECURITY", "Ошибка при попытке передать ID активации без переадресации, или же завершенной/не активной активации.", "An error occurred when trying to transfer an activation ID without forwarding, or a completed / inactive activation."),
  NOT_AVAILABLE("NOT_AVAILABLE ", "Для страны, которую вы используете, недоступна покупка мультисервисов.", "Country does not supported multiservice."),
  WRONG_ADDITIONAL_SERVICE("WRONG_ADDITIONAL_SERVICE", "Неверный дополнительный сервис (допустимы только сервисы для переадресации).", "Invalid additional service (only services for forwarding are allowed)."),
  REPEAT_ADDITIONAL_SERVICE("REPEAT_ADDITIONAL_SERVICE", "Ошибка возникает при попытке заказать купленный сервис еще раз.", "The error occurs when you try to order the purchased service again."),
  INCORECT_STATUS("INCORECT_STATUS", "Отсутствует или неправильно указан статус.", "Missing or incorrect status."),
  UNKNOWN("UNKNOWN", "Неизвестная ошибка.", "Unknown error.");

  /**
   * Response from server.
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
   * Constructor WrongParameter with multilang.
   *
   * @param russianMessage error message on russian.
   * @param englishMessage error message on england.
   * @param response response from server.
   */
  WrongParameter(@NotNull String response, @NotNull String russianMessage, @NotNull String englishMessage) {
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
   * Returns the wrongParameter by name.
   * @param name name wrong parameter.
   * @return wrongParameter if contains, else unknown.
   */
  @NotNull
  public static WrongParameter getWrongParameterByName(@NotNull String name) {
    name = name.toUpperCase();

    for (WrongParameter wrongParameter : values()) {
      if (wrongParameter.response.equals(name)) {
       return wrongParameter;
      }
    }

    return UNKNOWN;
  }
}
