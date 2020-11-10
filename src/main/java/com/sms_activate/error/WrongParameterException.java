package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class WrongParameterException extends BaseSMSActivateException {
  public WrongParameterException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
