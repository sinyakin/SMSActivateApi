package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class WrongResponse extends BaseSMSActivateException {
  /**
   * Constructor sms activate exception with multilang.
   *
   * @param englishMessage message on england language.
   * @param russianMessage message on russian language.
   */
  public WrongResponse(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
