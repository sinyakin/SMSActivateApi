package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public enum WrongParameter {
  BAD_ACTION("BAD_ACTION", "Некорректное действие.", "Wrong action."),
  BAD_SERVICE("BAD_SERVICE", "Некорректное наименование сервиса.", "Wrong name service."),
  BAD_KEY("BAD_KEY", "Неверный API-ключ.", "Wrong api-key."),
  BAD_STATUS("BAD_STATUS", "Попытка установить несуществующий статус.", "An attempt to establish a non-existent status."),
  WRONG_OPERATOR("WRONG_OPERATOR", "Некорректный оператор.", "Wrong operator"),
  WRONG_EXCEPTION_PHONE("WRONG_EXCEPTION_PHONE", "Некорректные исключающие префиксы.", "Wrong exception prefix."),
  WRONG_SERVICE("WRONG_SERVICE", "Некорректные сервисы.", "Wrong services."),
  NOT_AVAILABLE("NOT_AVAILABLE ", "Для страны, которую вы используете, недоступна покупка мультисервисов.", "Country does not supported multiservice."),
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
   * Returns the response.
   * @return response.
   */
  @NotNull
  protected String getResponse() {
    return response;
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
    for (WrongParameter wrongParameter : values()) {
      if (wrongParameter.response.equalsIgnoreCase(name)) {
       return wrongParameter;
      }
    }

    return UNKNOWN;
  }
}
