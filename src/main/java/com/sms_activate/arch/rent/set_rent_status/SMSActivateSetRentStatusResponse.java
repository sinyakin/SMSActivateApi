package com.sms_activate.arch.rent.set_rent_status;

import com.sms_activate.arch.main_response.SMSActivateMainResponse;
import com.sms_activate.arch.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateSetRentStatusResponse extends SMSActivateMainResponse {
  private final SMSActivateRentStatusRequest smsActivateRentStatusRequest;

  public SMSActivateSetRentStatusResponse(@NotNull SMSActivateRentStatusRequest smsActivateRentStatusRequest) {
    super(SMSActivateMainStatusResponse.SUCCESS);
    this.smsActivateRentStatusRequest = smsActivateRentStatusRequest;
  }

  @NotNull
  public SMSActivateRentStatusRequest getSMSActivateRentStatusRequest() {
    return smsActivateRentStatusRequest;
  }

  @NotNull
  public String getMessage() {
    return smsActivateRentStatusRequest.getMessage();
  }
}
