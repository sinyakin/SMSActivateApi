package com.sms_activate.error;

import com.sms_activate.error.common.BaseSMSActivateException;
import org.jetbrains.annotations.NotNull;

public class BannedException extends BaseSMSActivateException {
  public BannedException(@NotNull String date) {
    this("Your account has been banned on " + date, "Ваш аккаунт забанен на " + date);
  }

  /**
   * Constructor sms activate exception with multilang.
   *
   * @param englishMessage message on england language.
   * @param russianMessage message on russian language.
   */
  public BannedException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
