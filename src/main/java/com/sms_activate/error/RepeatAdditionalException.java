package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class RepeatAdditionalException extends BaseSMSActivateException {
  public RepeatAdditionalException(@NotNull String englandMessage, @NotNull String russianMessage) {
    super(englandMessage, russianMessage);
  }
}
