package com.sms_activate.old.error.common;

import com.sms_activate.old.error.WrongResponseException;
import com.sms_activate.old.error.type.WrongParameter;
import org.jetbrains.annotations.NotNull;

public class WrongParameterException extends SMSActivateBaseException {
  public WrongParameterException(@NotNull WrongParameter wrongParameter) {
    super(wrongParameter.getEnglishMessage(), wrongParameter.getRussianMessage());
  }

  /**
   * Constructor base sms activate exception with multilang.
   *
   * @param englishMessage message on english language.
   * @param russianMessage message on russian language.
   */
  public WrongParameterException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
