package com.sms_activate.rent.get_rent_services_and_countries;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SMSActivateGetRentServicesAndCountriesResponse {
  private final Set<String> operatorNameSet;
  private final Set<Integer> countryIdSet;
  private final Map<String, SMSActivateRentService> smsActivateRentServiceMap; // key is name service

  public SMSActivateGetRentServicesAndCountriesResponse(
      @NotNull Set<String> operatorNameSet,
      @NotNull Set<Integer> countryIdSet,
      @NotNull Map<String, SMSActivateRentService> smsActivateRentServiceMap
  ) {
    this.countryIdSet = countryIdSet;
    this.operatorNameSet = operatorNameSet;
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
  public Set<Integer> getCountryIdSet() {
    return countryIdSet;
  }

  @NotNull
  public Set<String> getOperatorNameSet() {
    return operatorNameSet;
  }
}
