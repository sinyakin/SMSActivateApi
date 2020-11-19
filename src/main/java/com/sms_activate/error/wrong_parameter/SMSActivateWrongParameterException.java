package com.sms_activate.error.wrong_parameter;

import com.sms_activate.error.base.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

/**
 * This exception occurs if one or more parameters that were passed are invalid or do not exist.
 */
public class SMSActivateWrongParameterException extends SMSActivateBaseException {
  /**
   * Constructor wrong parameter exception with multilang.
   *
   * @param smsActivateWrongParameter specified constant with description.
   */
  public SMSActivateWrongParameterException(@NotNull SMSActivateWrongParameter smsActivateWrongParameter) {
    super(smsActivateWrongParameter.getEnglishMessage(), smsActivateWrongParameter.getRussianMessage());
  }

  /**
   * Constructor wrong parameter exception with multilang.
   *
   * @param englishMessage message on english language.
   * @param russianMessage message on russian language.
   */
  public SMSActivateWrongParameterException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
