package com.sms_activate;

import com.sms_activate.respone.rent.extra.SMSActivateGetRentNumber;
import org.jetbrains.annotations.NotNull;

class SMSActivateGetRentNumberResponse {
  /**
   * Rent phone.
   */
  private SMSActivateGetRentNumber phone;

  /**
   * Returns the rent phone.
   *
   * @return rent phone.
   */
  @NotNull
  public SMSActivateGetRentNumber getSMSmsActivateGetRentNumber() {
    return phone;
  }
}
