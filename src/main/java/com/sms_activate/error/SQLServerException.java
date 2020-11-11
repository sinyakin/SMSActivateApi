package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class SQLServerException extends BaseSMSActivateException {
  public SQLServerException() {
    this("Error happened on SQL server", "Ошибка произошла на SQL сервере.");
  }

  /**
   * Constructor sms activate exception with multilang.
   *
   * @param englishMessage message on england language.
   * @param russianMessage message on russian language.
   */
  public SQLServerException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
