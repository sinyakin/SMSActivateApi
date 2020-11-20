package com.sms_activate.activation.current_activation;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetCurrentActivationResponse {
  private final int id;
  private final boolean forward;
  private final String number;
  private final String serviceName;
  private final int countryId;

  public SMSActivateGetCurrentActivationResponse(
      int id,
      boolean forward,
      @NotNull String number,
      int countryId,
      @NotNull String serviceName
  ) {
    this.id = id;
    this.forward = forward;
    this.number = number;
    this.serviceName = serviceName;
    this.countryId = countryId;
  }

  public int getId() {
    return id;
  }

  public boolean isForward() {
    return forward;
  }

  @NotNull
  public String getNumber() {
    return number;
  }

  @NotNull
  public String getServiceName() {
    return serviceName;
  }

  public int getCountryId() {
    return countryId;
  }
}
