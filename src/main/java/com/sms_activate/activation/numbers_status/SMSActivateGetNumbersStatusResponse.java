package com.sms_activate.activation.numbers_status;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMSActivateGetNumbersStatusResponse {
  private final Map<String, SMSActivateGetNumberStatusResponse> smsActivateGetNumberStatusResponseMap;

  public SMSActivateGetNumbersStatusResponse(@NotNull Map<String, SMSActivateGetNumberStatusResponse> smsActivateGetNumberStatusResponseMap) {
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
