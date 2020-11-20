package com.sms_activate.activation.country;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetCountriesResponse {
  private final List<SMSActivateGetCountryResponse> smsActivateGetCountryResponseList;

  public SMSActivateGetCountriesResponse(@NotNull List<SMSActivateGetCountryResponse> smsActivateGetCountryResponseList) {
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
