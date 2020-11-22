package com.sms_activate.rent.get_rent_status;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetRentStatusResponse {
  /**
   * Count sms.
   */
  private final int quantity;

  /**
   * SMS list from server.
   */
  private final List<SMSActivateSMS> smsActivateSMSList;

  /**
   * Constructor response getRentStatusResponse with data.
   *
   * @param quantity           count sms.
   * @param smsActivateSMSList list sms from server.
   */
  public SMSActivateGetRentStatusResponse(int quantity, @NotNull List<SMSActivateSMS> smsActivateSMSList) {
    this.quantity = quantity;
    this.smsActivateSMSList = smsActivateSMSList;
  }

  /**
   * Returns the count sms.
   *
   * @return count sms.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Returns the list sms from server.
   *
   * @return list sms from server.
   */
  @NotNull
  public List<SMSActivateSMS> getSmsActivateSMSList() {
    return smsActivateSMSList;
  }
}
