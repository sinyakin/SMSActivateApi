package com.sms_activate.error.rent;

import com.sms_activate.error.BaseSMSActivateException;
import org.jetbrains.annotations.NotNull;

public class RentException extends BaseSMSActivateException {
  public RentException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
