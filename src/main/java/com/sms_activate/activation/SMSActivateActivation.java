package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

public class SMSActivateActivation {
  private final int id;
  private final boolean forward;
  private final String number;
  private final String serviceName;

  public SMSActivateActivation(
      int id,
      @NotNull String number,
      @NotNull String serviceName,
      boolean forward
  ) {
    this.id = id;
    this.number = number;
    this.serviceName = serviceName;
    this.forward = forward;
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
}
