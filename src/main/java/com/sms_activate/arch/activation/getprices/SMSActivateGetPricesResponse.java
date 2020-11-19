package com.sms_activate.arch.activation.getprices;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class SMSActivateGetPricesResponse extends SMSActivateMainResponse {
  // key list is ID
  // ket map is service name
  private final List<Map<String, SMSActivateGetPriceResponse>> smsActivateGetPriceMapList;


  public SMSActivateGetPricesResponse(@NotNull List<Map<String, SMSActivateGetPriceResponse>> smsActivateGetPriceMapList) {
    super(SMSActivateMainStatusResponse.SUCCESS);
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
