package com.sms_activate.activation.current_activation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SMSActivateGetCurrentActivationsResponse {
  private final Map<Integer, SMSActivateGetCurrentActivationResponse> smsActivateGetCurrentActivationResponseMap;

  // key is ID activation
  public SMSActivateGetCurrentActivationsResponse(@NotNull Map<Integer, SMSActivateGetCurrentActivationResponse> smsActivateGetCurrentActivationResponseMap) {
    this.smsActivateGetCurrentActivationResponseMap = smsActivateGetCurrentActivationResponseMap;
  }

  @Nullable
  public SMSActivateGetCurrentActivationResponse get(int idActivation) {
    return smsActivateGetCurrentActivationResponseMap.get(idActivation);
  }

  @NotNull
  public Set<Integer> getIdSet() {
    return smsActivateGetCurrentActivationResponseMap.keySet();
  }

  @NotNull
  public Set<SMSActivateGetCurrentActivationResponse> getSMSActivateGetCurrentActivationResponseSet() {
    return new HashSet<>(smsActivateGetCurrentActivationResponseMap.values());
  }

  public boolean haveActivation() {
    return this.smsActivateGetCurrentActivationResponseMap.isEmpty();
  }
}
