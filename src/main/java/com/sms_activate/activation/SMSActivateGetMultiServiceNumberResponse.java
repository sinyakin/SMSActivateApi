package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetMultiServiceNumberResponse {
  private final List<SMSActivateActivation> smsActivateActivationList;

  public SMSActivateGetMultiServiceNumberResponse(@NotNull List<SMSActivateActivation> smsActivateActivationList) {
    this.smsActivateActivationList = smsActivateActivationList;
  }

  @NotNull
  public SMSActivateActivation get(int id) {
    return this.smsActivateActivationList.get(id);
  }

  @NotNull
  public List<SMSActivateActivation> getSMSActivateActivationList() {
    return smsActivateActivationList;
  }
}
