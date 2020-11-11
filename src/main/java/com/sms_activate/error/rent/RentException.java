package com.sms_activate.error.rent;

import com.sms_activate.error.BaseSMSActivateException;
import org.jetbrains.annotations.NotNull;

public class RentException extends BaseSMSActivateException {
  /**
   * Constructor base rent sms activate exception with multilang.
   *
   * @param englishMessage message on england language.
   * @param russianMessage message on russian language.
   */
  public RentException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
