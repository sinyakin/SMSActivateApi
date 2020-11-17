package com.sms_activate.arch;

import org.jetbrains.annotations.NotNull;

public class SMSActivateMainResponse {
  private final SMSActivateStatusResponse status;

  public SMSActivateMainResponse(@NotNull SMSActivateStatusResponse status) {
    this.status = status;
  }

  public SMSActivateStatusResponse getStatus() {
    return status;
  }
}
