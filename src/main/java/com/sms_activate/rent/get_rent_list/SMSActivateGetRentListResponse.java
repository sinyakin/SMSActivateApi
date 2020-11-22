package com.sms_activate.rent.get_rent_list;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetRentListResponse {
  /**
   *
   */
  private final List<SMSActivateGetRentResponse> smsActivateGetRentResponseList;

  /**
   * Constructor response getRentList with list current rents.
   * @param smsActivateGetRentResponseList list current rents.
   */
  public SMSActivateGetRentListResponse(@NotNull List<SMSActivateGetRentResponse> smsActivateGetRentResponseList) {
    this.smsActivateGetRentResponseList = smsActivateGetRentResponseList;
  }

  /**
   * Returns the rent by index.
   * @param i index rent.
   * @return rent object.
   */
  public SMSActivateGetRentResponse get(int i) {
    return this.smsActivateGetRentResponseList.get(i);
  }

  /**
   * Returns the list current rents.
   * @return the list current rents.
   */
  @NotNull
  public List<SMSActivateGetRentResponse> getSmsActivateGetRentResponseList() {
    return smsActivateGetRentResponseList;
  }
}
