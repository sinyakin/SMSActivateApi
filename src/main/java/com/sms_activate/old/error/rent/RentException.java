package com.sms_activate.old.error.rent;

import com.sms_activate.old.error.common.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

public class RentException extends SMSActivateBaseException {
  /**
   * Constructor base rent sms activate exception with multilang.
   *
   * @param englishMessage message on english language.
   * @param russianMessage message on russian language.
   */
  public RentException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
