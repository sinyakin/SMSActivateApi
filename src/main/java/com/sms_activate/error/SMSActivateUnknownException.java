package com.sms_activate.error;

import com.sms_activate.error.base.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMSActivateUnknownException extends SMSActivateBaseException {
  /**
   * Message unknown error
   */
  private final String messageUnknownError;

  /**
   * Constructor unknown sms activate exception with multilang.
   *
   * @param error name not documented error.
   */
  public SMSActivateUnknownException(@NotNull String error, @Nullable String messageUnknownError) {
    super(error, "");
    this.messageUnknownError = messageUnknownError;
  }

  @Nullable
  public String getMessageUnknownError() {
    return messageUnknownError;
  }
}
