package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class NoNumberException extends BaseSMSActivateException {
  public NoNumberException() {
    this("No numbers.", "Нет номеров.");
  }

  public NoNumberException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
