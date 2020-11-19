package com.sms_activate.activation.current_activation;

import com.sms_activate.main_response.SMSActivateMainResponse;
import com.sms_activate.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
  public List<SMSActivateGetCurrentActivationResponse> getSMSActivateGetCurrentActivationResponseList() {
    return new ArrayList<>(smsActivateGetCurrentActivationResponseMap.values());
  }
}
