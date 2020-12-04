package com.sms_activate.respone.rent;

import com.sms_activate.respone.rent.extra.SMSActivateSMS;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMSActivateGetRentStatusResponse {
  /**
   * Count sms.
   */
  private int quantity;

  /**
   * Service name.
   */
  private String service;

  /**
   * SMS list from server.
   */
  private Map<Integer, SMSActivateSMS> values;

  /**
   * Returns the count sms.
   *
   * @return count sms.
   */
  public int getCountSms() {
    return quantity;
  }

  /**
   * Returns the list sms from server.
   *
   * @return list sms from server.
   */
  @NotNull
  public List<SMSActivateSMS> getSmsActivateSMSList() {
    return new ArrayList<>(values.values());
  }

  /**
   * Returns the service name.
   *
   * @return service name
   */
  @NotNull
  public String getService() {
    return service;
  }
}
