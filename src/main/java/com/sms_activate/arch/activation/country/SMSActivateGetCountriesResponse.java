package com.sms_activate.arch.activation.country;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetCountriesResponse extends SMSActivateMainResponse {
  private final List<SMSActivateGetCountryResponse> smsActivateGetCountryResponseList;

  public SMSActivateGetCountriesResponse(@NotNull List<SMSActivateGetCountryResponse> smsActivateGetCountryResponseList) {
    super(SMSActivateStatusResponse.SUCCESS);
    this.smsActivateGetCountryResponseList = smsActivateGetCountryResponseList;
  }

  @NotNull
  public SMSActivateGetCountryResponse get(int id) {
    return this.smsActivateGetCountryResponseList.get(id);
  }

  @NotNull
  public List<SMSActivateGetCountryResponse> getSMSActivateGetCountryResponseList() {
    return this.smsActivateGetCountryResponseList;
  }
}
