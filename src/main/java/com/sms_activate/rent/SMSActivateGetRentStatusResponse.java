package com.sms_activate.rent;

import com.sms_activate.rent.extra.SMSActivateSMS;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetRentStatusResponse {
  /**
   * Count sms.
   */
  private final int countSms;

  /**
   * SMS list from server.
   */
  private final List<SMSActivateSMS> smsActivateSMSList;

  /**
   * Constructor response getRentStatusResponse with data from server.
   *
   * @param countSms           count sms.
   * @param smsActivateSMSList list sms from server.
   */
  public SMSActivateGetRentStatusResponse(int countSms, @NotNull List<SMSActivateSMS> smsActivateSMSList) {
    this.countSms = countSms;
    this.smsActivateSMSList = smsActivateSMSList;
  }

  /**
   * Returns the count sms.
   *
   * @return count sms.
   */
  public int getCountSms() {
    return countSms;
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
