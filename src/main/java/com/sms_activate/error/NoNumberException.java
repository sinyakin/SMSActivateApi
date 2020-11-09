package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class NoNumberException extends BaseSMSActivateException {
  public NoNumberException(@NotNull String englandMessage, @NotNull String russianMessage) {
    super(englandMessage, russianMessage);
  }
}
