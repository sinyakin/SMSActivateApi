package com.sms_activate.activation.get_prices;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMSActivateGetPricesResponse {
  // key list is ID country
  // key map is service name
  private final Map<Integer, Map<String, SMSActivateGetPriceResponse>> smsActivateGetPriceMap;


  public SMSActivateGetPricesResponse(@NotNull Map<Integer,Map<String, SMSActivateGetPriceResponse>> smsActivateGetPriceMapList) {
    this.smsActivateGetPriceMap = smsActivateGetPriceMapList;
  }

  @NotNull
  public SMSActivateGetPriceResponse get(int countryId, @NotNull String serviceName) {
    return getPricesByCountry(countryId).get(serviceName);
  }

  @NotNull
  public Map<String, SMSActivateGetPriceResponse> getPricesByCountry(int countryId) {
    return this.smsActivateGetPriceMap.get(countryId);
  }

  @NotNull
  public Map<Integer, Map<String, SMSActivateGetPriceResponse>> getSmsActivateGetPriceMap() {
    return smsActivateGetPriceMap;
  }
}
