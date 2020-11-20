package com.sms_activate.activation.current_activation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SMSActivateGetCurrentActivationsResponse {
  private final Map<Integer, SMSActivateGetCurrentActivationResponse> smsActivateGetCurrentActivationResponseMap;

  // key is ID
  public SMSActivateGetCurrentActivationsResponse(@NotNull Map<Integer, SMSActivateGetCurrentActivationResponse> smsActivateGetCurrentActivationResponseMap) {
    this.smsActivateGetCurrentActivationResponseMap = smsActivateGetCurrentActivationResponseMap;
  }

  @Nullable
  public SMSActivateGetCurrentActivationResponse get(int id) {
    return smsActivateGetCurrentActivationResponseMap.get(id);
  }

  @NotNull
  public Set<Integer> getIdSet() {
    return smsActivateGetCurrentActivationResponseMap.keySet();
  }

  @NotNull
  public Set<SMSActivateGetCurrentActivationResponse> getSMSActivateGetCurrentActivationResponseSet() {
    return new HashSet<>(smsActivateGetCurrentActivationResponseMap.values());
  }
}
