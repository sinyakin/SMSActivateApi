package com.sms_activate.activation.get_prices;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class SMSActivateGetPricesResponse {
  // key list is ID
  // key map is service name
  private final List<Map<String, SMSActivateGetPriceResponse>> smsActivateGetPriceMapList;


  public SMSActivateGetPricesResponse(@NotNull List<Map<String, SMSActivateGetPriceResponse>> smsActivateGetPriceMapList) {
    this.smsActivateGetPriceMapList = smsActivateGetPriceMapList;
  }

  @NotNull
  public SMSActivateGetPriceResponse get(int countryId, @NotNull String serviceName) {
    return getPricesByCountry(countryId).get(serviceName);
  }

  @NotNull
  public Map<String, SMSActivateGetPriceResponse> getPricesByCountry(int countryId) {
    return this.smsActivateGetPriceMapList.get(countryId);
  }

  @NotNull
  public List<Map<String, SMSActivateGetPriceResponse>> getSmsActivateGetPriceMapList() {
    return smsActivateGetPriceMapList;
  }
}
