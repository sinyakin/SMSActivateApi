package com.sms_activate.arch.activation;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetMultiServiceNumberResponse extends SMSActivateMainResponse {
  private final List<SMSActivateActivation> smsActivateActivationList;

  public SMSActivateGetMultiServiceNumberResponse(@NotNull List<SMSActivateActivation> smsActivateActivationList) {
    super(SMSActivateStatusResponse.SUCCESS);
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
