package com.sms_activate.rent;

import com.sms_activate.rent.extra.SMSActivateGetRentNumber;
import org.jetbrains.annotations.NotNull;

public class SMSActivateGetRentNumberResponse {
  /**
   * Status.
   */
  private String status;

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
  public SMSActivateGetRentNumber getRentPhone() {
    return phone;
  }

  /**
   * Returns the status response.
   *
   * @return status response.
   */
  @NotNull
  public String getStatus() {
    return status;
  }
}
