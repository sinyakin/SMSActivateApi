package com.sms_activate.activation.numbers_status;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMSActivateGetNumbersStatusResponse {
  /**
   * Map services where key is short name service.
   */
  private final Map<String, SMSActivateGetNumberStatusResponse> smsActivateGetNumberStatusResponseMap;

  /**
   * Constructor response getNumbersStatus with services.
   *
   * @param smsActivateGetNumberStatusResponseMap map services where key is short name service.
   */
  public SMSActivateGetNumbersStatusResponse(@NotNull Map<String, SMSActivateGetNumberStatusResponse> smsActivateGetNumberStatusResponseMap) {
    this.smsActivateGetNumberStatusResponseMap = smsActivateGetNumberStatusResponseMap;
  }

  /**
   * Returns the service by shortName.
   *
   * @param serviceName service short name.
   * @return if serviceName not contains in map then returns null else service object.
   */
  @Nullable
  public SMSActivateGetNumberStatusResponse get(@NotNull String serviceName) {
    return smsActivateGetNumberStatusResponseMap.get(serviceName);
  }

  /**
   * Returns the all services.
   *
   * @return all services.
   */
  @NotNull
  public List<SMSActivateGetNumberStatusResponse> getSMSActivateGetNumberStatusResponseList() {
    return new ArrayList<>(smsActivateGetNumberStatusResponseMap.values());
  }
}
