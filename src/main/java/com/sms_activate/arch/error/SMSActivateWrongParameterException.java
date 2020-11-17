package com.sms_activate.arch.error;

import org.jetbrains.annotations.NotNull;

public class SMSActivateWrongParameterException extends SMSActivateBaseException {
  public SMSActivateWrongParameterException(@NotNull SMSActivateWrongParameter smsActivateWrongParameter) {
    super(smsActivateWrongParameter.getEnglishMessage(), smsActivateWrongParameter.getRussianMessage());
  }

  /**
   * Constructor base sms activate exception with multilang.
   *
   * @param englishMessage message on english language.
   * @param russianMessage message on russian language.
   */
  public SMSActivateWrongParameterException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
