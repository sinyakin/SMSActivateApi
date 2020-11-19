package com.sms_activate.arch.activation;

import com.sms_activate.arch.main_response.SMSActivateMainResponse;
import com.sms_activate.arch.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateActivation extends SMSActivateMainResponse {
  private int id;
  private boolean forward;
  private String number = "";
  private String serviceName = "";

  public SMSActivateActivation() {
    super(SMSActivateMainStatusResponse.SUCCESS);
  }

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
