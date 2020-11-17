package com.sms_activate.arch;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetCurrentActivation extends SMSActivateMainResponse {
  private final int id;
  private final boolean forward;
  private final String number;
  private final String serviceName;
  private final String countryName;

  public SMSActivateGetCurrentActivation(
      @NotNull SMSActivateStatusResponse status,
      int id,
      boolean forward,
      @NotNull String number,
      @NotNull String serviceName,
      @NotNull String countryName
  ) {
    super(status);
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
