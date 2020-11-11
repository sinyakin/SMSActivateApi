package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class NoBalanceException extends BaseSMSActivateException {
  public NoBalanceException() {
    this("Balance in your account is zero.", "Нет денег на счету.");
  }

  /**
   * Constructor sms activate exception with multilang.
   *
   * @param englishMessage message on england language.
   * @param russianMessage message on russian language.
   */
  public NoBalanceException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
