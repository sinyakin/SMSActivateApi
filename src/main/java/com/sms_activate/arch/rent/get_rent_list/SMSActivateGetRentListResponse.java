package com.sms_activate.arch.rent.get_rent_list;

import com.sms_activate.arch.main_response.SMSActivateMainResponse;
import com.sms_activate.arch.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetRentListResponse extends SMSActivateMainResponse {
  private final List<SMSActivateGetRentResponse> smsActivateGetRentResponseList;

  public SMSActivateGetRentListResponse(@NotNull List<SMSActivateGetRentResponse> smsActivateGetRentResponseList) {
    super(SMSActivateMainStatusResponse.SUCCESS);
    this.smsActivateGetRentResponseList = smsActivateGetRentResponseList;
  }

  @NotNull
  public List<SMSActivateGetRentResponse> getSmsActivateGetRentResponseList() {
    return smsActivateGetRentResponseList;
  }
}
