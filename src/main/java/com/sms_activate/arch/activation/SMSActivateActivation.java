package com.sms_activate.arch.activation;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateActivation extends SMSActivateMainResponse {
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
    super(SMSActivateMainStatusResponse.SUCCESS);
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

  public String getNumber() {
    return number;
  }

  public String getServiceName() {
    return serviceName;
  }
}
