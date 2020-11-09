package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class NoBalanceException extends BaseSMSActivateException {
  public NoBalanceException(@NotNull String englandMessage, @NotNull String russianMessage) {
    super(englandMessage, russianMessage);
  }
}
