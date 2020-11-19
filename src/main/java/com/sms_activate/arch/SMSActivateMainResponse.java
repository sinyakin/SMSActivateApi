package com.sms_activate.arch;

import org.jetbrains.annotations.NotNull;

public class SMSActivateMainResponse {
  private final SMSActivateMainStatusResponse status;

  public SMSActivateMainResponse(@NotNull SMSActivateMainStatusResponse status) {
    this.status = status;
  }

  @NotNull
  public SMSActivateMainStatusResponse getStatus() {
    return status;
  }
}
