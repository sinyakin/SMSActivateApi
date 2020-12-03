package com.sms_activate.rent;

import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.extra.SMSActivateRentService;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SMSActivateGetRentServicesAndCountriesResponse {
  /**
   * Set with name operators.
   */
  private Map<Integer, String> operators;

  /**
   * Set with id countries.
   */
  private Map<Integer, Integer> countries;

  /**
   * Map rent services where key is short name service.
   */
  private Map<String, SMSActivateRentService> services;

  private SMSActivateGetRentServicesAndCountriesResponse() {
  }

  /**
   * Returns the service rent by name.
   *
   * @param name short service name.
   * @return service rent.
   */
  @NotNull
  public SMSActivateRentService getRentService(@NotNull String name) throws SMSActivateWrongParameterException {
    SMSActivateRentService rentService = services.get(name);

    if (rentService == null) {
      throw new SMSActivateWrongParameterException("Service name is incorrect", "Неккоректное имя сервиса.");
    }

    return rentService;
  }

  /**
   * Returns the all rent services.
   *
   * @return all rent services.
   */
  @NotNull
  public List<SMSActivateRentService> getAllRentServices() {
    return new ArrayList<>(services.values());
  }

  /**
   * Returns the list rent services.
   *
   * @return list rent services.
   */
  @NotNull
  public Set<String> getRentServices() {
    return services.keySet();
  }

  /**
   * Returns the set countries supported rent.
   *
   * @return set countries supported rent.
   */
  @NotNull
  public Set<Integer> getCountryIdSet() {
    return new HashSet<>(countries.values());
  }

  /**
   * Returns the set operators supported rent.
   *
   * @return set operators supported rent.
   */
  @NotNull
  public Set<String> getOperatorNameSet() {
    return new HashSet<>(operators.values());
  }
}
