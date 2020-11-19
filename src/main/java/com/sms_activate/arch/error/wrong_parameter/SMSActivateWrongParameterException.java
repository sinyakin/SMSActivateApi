package com.sms_activate.arch.error.wrong_parameter;

import com.sms_activate.arch.error.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This exception occurs if one or more parameters that were passed are invalid or do not exist.
 */
public class SMSActivateWrongParameterException extends SMSActivateBaseException {
  /**
   * Specified constant with description about wrong parameter.
   */
  private SMSActivateWrongParameter smsActivateWrongParameter = null;

  /**
   * Constructor wrong parameter exception with multilang.
   *
   * @param smsActivateWrongParameter specified constant with description.
   */
  public SMSActivateWrongParameterException(@Nullable SMSActivateWrongParameter smsActivateWrongParameter) {
    super(smsActivateWrongParameter.getEnglishMessage(), smsActivateWrongParameter.getRussianMessage());
    this.smsActivateWrongParameter = smsActivateWrongParameter;
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

  /**
   * Returns the specified constant with description.
   *
   * @return specified constant with description.
   */
  @Nullable
  public SMSActivateWrongParameter getSMSActivateWrongParameter() {
    return smsActivateWrongParameter;
  }
}
