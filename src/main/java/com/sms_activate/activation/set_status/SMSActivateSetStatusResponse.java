package com.sms_activate.activation.set_status;

import com.sms_activate.main_response.SMSActivateMainResponse;
import com.sms_activate.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateSetStatusResponse {
  private final SMSActivateAccessStatus smsActivateAccessStatus;

  public SMSActivateSetStatusResponse(@NotNull SMSActivateAccessStatus smsActivateAccessStatus) {
    this.smsActivateAccessStatus = smsActivateAccessStatus;
  }

  @NotNull
  public String getMessage() {
    return smsActivateAccessStatus.getMessage();
  }

  @NotNull
  public SMSActivateAccessStatus getSMSActivateAccessStatus() {
    return smsActivateAccessStatus;
  }
}