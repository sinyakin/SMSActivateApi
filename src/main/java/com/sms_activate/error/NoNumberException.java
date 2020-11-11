package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class NoNumberException extends BaseSMSActivateException {
  public NoNumberException() {
    this("No numbers.", "Нет номеров.");
  }

  /**
   * Constructor sms activate exception with multilang.
   *
   * @param englishMessage message on england language.
   * @param russianMessage message on russian language.
   */
  public NoNumberException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
