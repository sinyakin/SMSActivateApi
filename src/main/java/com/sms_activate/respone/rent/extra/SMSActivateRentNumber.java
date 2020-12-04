package com.sms_activate.respone.rent.extra;

public class SMSActivateRentNumber {
  /**
   * Id rent.
   */
  private int id;

  /**
   * Phone number.
   */
  private long phone;

  private SMSActivateRentNumber() {
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
   * Phone number.
   *
   * @return phone number.
   */
  public long getNumber() {
    return phone;
  }
}
