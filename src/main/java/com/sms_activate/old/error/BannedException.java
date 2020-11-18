package com.sms_activate.old.error;

import com.sms_activate.old.error.common.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

public class BannedException extends SMSActivateBaseException {
  public BannedException(@NotNull String date) {
    this("Your account has been banned on " + date, "Ваш аккаунт забанен на " + date);
  }

  /**
   * Constructor sms activate exception with multilang.
   *
   * @param englishMessage message on english language.
   * @param russianMessage message on russian language.
   */
  public BannedException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}