package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class WrongParameterException extends BaseSMSActivateException {
  /**
   * Constructor sms activate exception with multilang.
   *
   * @param englishMessage message on england language.
   * @param russianMessage message on russian language.
   */
  public WrongParameterException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
