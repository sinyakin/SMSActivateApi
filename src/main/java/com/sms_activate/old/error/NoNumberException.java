package com.sms_activate.old.error;

import com.sms_activate.old.error.common.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

public class NoNumberException extends SMSActivateBaseException {
  public NoNumberException() {
    this("No numbers.", "Нет номеров.");
  }

  /**
   * Constructor sms activate exception with multilang.
   *
   * @param englishMessage message on english language.
   * @param russianMessage message on russian language.
   */
  public NoNumberException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
