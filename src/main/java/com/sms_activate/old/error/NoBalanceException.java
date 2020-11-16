package com.sms_activate.old.error;

import com.sms_activate.old.error.common.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

public class NoBalanceException extends SMSActivateBaseException {
  public NoBalanceException() {
    this("Balance in your account is zero.", "Нет денег на счету.");
  }

  /**
   * Constructor sms activate exception with multilang.
   *
   * @param englishMessage message on english language.
   * @param russianMessage message on russian language.
   */
  public NoBalanceException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
