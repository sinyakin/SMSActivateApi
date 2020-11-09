package com.sms_activate.error.rent;

import com.sms_activate.error.BaseSMSActivateException;
import org.jetbrains.annotations.NotNull;

public class RentException extends BaseSMSActivateException {
  public RentException(@NotNull String englandMessage, @NotNull String russianMessage) {
    super(englandMessage, russianMessage);
  }
}
