package com.sms_activate.rent;

import com.sms_activate.rent.extra.SMSActivateRentNumber;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetRentListResponse {
  /**
   *
   */
  private final List<SMSActivateRentNumber> smsActivateRentNumberList;

  /**
   * Constructor response getRentList with list current rents.
   * @param smsActivateRentNumberList list current rents.
   */
  public SMSActivateGetRentListResponse(@NotNull List<SMSActivateRentNumber> smsActivateRentNumberList) {
    this.smsActivateRentNumberList = smsActivateRentNumberList;
  }

  /**
   * Returns the rent by index.
   * @param i index rent.
   * @return rent object.
   */
  public SMSActivateRentNumber get(int i) {
    return this.smsActivateRentNumberList.get(i);
  }

  /**
   * Returns the list current rents.
   * @return the list current rents.
   */
  @NotNull
  public List<SMSActivateRentNumber> getSmsActivateGetRentResponseList() {
    return smsActivateRentNumberList;
  }
}
