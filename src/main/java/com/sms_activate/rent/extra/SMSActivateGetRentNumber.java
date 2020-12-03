package com.sms_activate.rent.extra;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetRentNumber {
  /**
   * Id rent.
   */
  private int id;

  /**
   * Phone number.
   */
  private long number;

  /**
   * End date of rent.
   */
  private String endDate;

  private SMSActivateGetRentNumber() {
  }

  /**
   * Returns the id rent.
   *
   * @return id rent.
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the phone number.
   *
   * @return phone number
   */
  public long getNumber() {
    return number;
  }

  /**
   * Returns the end date of rent.
   *
   * @return end date of rent.
   */
  @NotNull
  public String getEndDate() {
    return endDate;
  }
}
