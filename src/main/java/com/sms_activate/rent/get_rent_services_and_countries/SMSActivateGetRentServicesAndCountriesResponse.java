package com.sms_activate.rent.get_rent_services_and_countries;

import com.sms_activate.main_response.SMSActivateMainResponse;
import com.sms_activate.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMSActivateGetRentServicesAndCountriesResponse extends SMSActivateMainResponse {
  private final List<String> operatorNameList;
  private final List<Integer> countryIdList;
  private final Map<String, SMSActivateRentService> smsActivateRentServiceMap; // key is name service

  public SMSActivateGetRentServicesAndCountriesResponse(
      @NotNull List<String> operatorNameList,
      @NotNull List<Integer> countryIdList,
      @NotNull Map<String, SMSActivateRentService> smsActivateRentServiceMap
  ) {
    super(SMSActivateMainStatusResponse.SUCCESS);
    this.countryIdList = countryIdList;
    this.operatorNameList = operatorNameList;
    this.smsActivateRentServiceMap = smsActivateRentServiceMap;
  }

  @NotNull
  public SMSActivateRentService getService(@NotNull String name) {
    return smsActivateRentServiceMap.get(name);
  }

  @NotNull
  public List<SMSActivateRentService> getAllServices() {
    return new ArrayList<>(smsActivateRentServiceMap.values());
  }

  @NotNull
  public List<Integer> getCountryIdList() {
    return countryIdList;
  }

  @NotNull
  public List<String> getOperatorNameList() {
    return operatorNameList;
  }
}
