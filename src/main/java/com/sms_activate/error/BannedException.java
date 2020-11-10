package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class BannedException extends BaseSMSActivateException {
  public BannedException(@NotNull String date) {
    this("Your account has been banned on " + date, "Ваш аккаунт забанен на " + date);
  }

  public BannedException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
