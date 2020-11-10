package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class NoBalanceException extends BaseSMSActivateException {
  public NoBalanceException() {
    this("Balance in your account is zero.", "Нет денег на счету.");
  }

  public NoBalanceException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
