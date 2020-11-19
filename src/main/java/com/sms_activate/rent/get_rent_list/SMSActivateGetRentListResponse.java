package com.sms_activate.rent.get_rent_list;

import com.sms_activate.main_response.SMSActivateMainResponse;
import com.sms_activate.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetRentListResponse {
  private final List<SMSActivateGetRentResponse> smsActivateGetRentResponseList;

  public SMSActivateGetRentListResponse(@NotNull List<SMSActivateGetRentResponse> smsActivateGetRentResponseList) {
    this.smsActivateGetRentResponseList = smsActivateGetRentResponseList;
  }

  @NotNull
  public List<SMSActivateGetRentResponse> getSmsActivateGetRentResponseList() {
    return smsActivateGetRentResponseList;
  }
}
