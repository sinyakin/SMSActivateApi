package com.sms_activate.arch.rent.get_rent_sms;

import com.sms_activate.arch.main_response.SMSActivateMainResponse;
import com.sms_activate.arch.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetRentStatusResponse extends SMSActivateMainResponse {
  private final int quantity;
  private final List<SMSActivateSMS> smsActivateSMSList;

  public SMSActivateGetRentStatusResponse(int quantity, @NotNull List<SMSActivateSMS> smsActivateSMS) {
    super(SMSActivateMainStatusResponse.SUCCESS);
    this.quantity = quantity;
    this.smsActivateSMSList = smsActivateSMS;
  }

  public int getQuantity() {
    return quantity;
  }

  @NotNull
  public List<SMSActivateSMS> getSmsActivateSMSList() {
    return smsActivateSMSList;
  }
}
