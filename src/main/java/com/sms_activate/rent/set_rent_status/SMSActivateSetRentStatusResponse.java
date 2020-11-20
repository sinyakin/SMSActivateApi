package com.sms_activate.rent.set_rent_status;

import org.jetbrains.annotations.NotNull;

public class SMSActivateSetRentStatusResponse {
  private final SMSActivateRentStatus status;

  public SMSActivateSetRentStatusResponse(@NotNull SMSActivateRentStatus status) {
    this.status = status;
  }

  @NotNull
  public SMSActivateRentStatus getStatus() {
    return status;
  }
}
