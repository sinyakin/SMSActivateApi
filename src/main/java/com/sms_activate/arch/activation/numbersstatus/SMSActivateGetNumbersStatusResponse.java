package com.sms_activate.arch.activation.numbersstatus;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMSActivateGetNumbersStatusResponse extends SMSActivateMainResponse {
  private final Map<String, SMSActivateGetNumberStatusResponse> smsActivateGetNumberStatusResponseMap;

  public SMSActivateGetNumbersStatusResponse(@NotNull Map<String, SMSActivateGetNumberStatusResponse> smsActivateGetNumberStatusResponseMap) {
    super(SMSActivateMainStatusResponse.SUCCESS);
    this.smsActivateGetNumberStatusResponseMap = smsActivateGetNumberStatusResponseMap;
  }

  @NotNull
  public SMSActivateGetNumberStatusResponse get(@NotNull String serviceName) {
    return smsActivateGetNumberStatusResponseMap.get(serviceName);
  }

  @NotNull
  public List<SMSActivateGetNumberStatusResponse> getSMSActivateGetNumberStatusResponseList() {
    return new ArrayList<>(smsActivateGetNumberStatusResponseMap.values());
  }
}
