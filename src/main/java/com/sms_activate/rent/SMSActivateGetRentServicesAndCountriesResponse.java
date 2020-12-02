package com.sms_activate.rent;

import com.sms_activate.rent.extra.SMSActivateRentService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SMSActivateGetRentServicesAndCountriesResponse {
  /**
   * Set with name operators.
   */
  private final Set<String> operatorNameSet;

  /**
   * Set with id countries.
   */
  private final Set<Integer> countryIdSet;

  /**
   * Map rent services where key is short name service.
   */
  private final Map<String, SMSActivateRentService> smsActivateRentServiceMap;

  /**
   * Constructor response getRentServiceAndCountries with data from server.
   *
   * @param operatorNameSet           set with name operators.
   * @param countryIdSet              set with id countries.
   * @param smsActivateRentServiceMap map rent services where key is short name service.
   */
  public SMSActivateGetRentServicesAndCountriesResponse(
      @NotNull Set<String> operatorNameSet,
      @NotNull Set<Integer> countryIdSet,
      @NotNull Map<String, SMSActivateRentService> smsActivateRentServiceMap
  ) {
    this.countryIdSet = countryIdSet;
    this.operatorNameSet = operatorNameSet;
    this.smsActivateRentServiceMap = smsActivateRentServiceMap;
  }

  /**
   * Returns the service rent by name.
   *
   * @param name short service name.
   * @return service rent.
   */
  @Nullable
  public SMSActivateRentService getService(@NotNull String name) {
    return smsActivateRentServiceMap.get(name);
  }

  /**
   * Returns the list rent services.
   *
   * @return list rent services.
   */
  @NotNull
  public List<SMSActivateRentService> getAllServices() {
    return new ArrayList<>(smsActivateRentServiceMap.values());
  }

  /**
   * Returns the set countries supported rent.
   *
   * @return set countries supported rent.
   */
  @NotNull
  public Set<Integer> getCountryIdSet() {
    return countryIdSet;
  }

  /**
   * Returns the set operators supported rent.
   *
   * @return set operators supported rent.
   */
  @NotNull
  public Set<String> getOperatorNameSet() {
    return operatorNameSet;
  }
}
