package com.sms_activate.error;

import com.sms_activate.error.base.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

public class SMSActivateUnknownException extends SMSActivateBaseException {
  /**
   * Constructor base sms activate exception with multilang.
   *
   * @param error name not documented error.
   */
  public SMSActivateUnknownException(@NotNull String error) {
    super(error, "");
  }
}
