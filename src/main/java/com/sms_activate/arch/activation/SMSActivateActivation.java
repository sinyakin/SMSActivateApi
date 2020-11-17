package com.sms_activate.arch.activation;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateActivation extends SMSActivateMainResponse {
  private final int id;
  private final boolean forward;
  private final String number;
  private final String serviceName;

  public SMSActivateActivation(
      @NotNull SMSActivateStatusResponse status,
      int id,
      boolean forward,
      @NotNull String number,
      @NotNull String serviceName
  ) {
    super(status);
    this.id = id;
    this.forward = forward;
    this.number = number;
    this.serviceName = serviceName;
  }


  public int getId() {
    return id;
  }

  public boolean isForward() {
    return forward;
  }

  public String getNumber() {
    return number;
  }

  public String getServiceName() {
    return serviceName;
  }
}
