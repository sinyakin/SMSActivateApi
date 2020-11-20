package com.sms_activate.activation.current_activation;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetCurrentActivationResponse {
  private final int id;
  private final boolean forward;
  private final String number;
  private final String serviceName;
  private final String countryName;

  public SMSActivateGetCurrentActivationResponse(
      int id,
      boolean forward,
      @NotNull String number,
      @NotNull String serviceName,
      @NotNull String countryName
  ) {
    this.id = id;
    this.forward = forward;
    this.number = number;
    this.serviceName = serviceName;
    this.countryName = countryName;
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

  @NotNull
  public String getCountryName() {
    return countryName;
  }
}
