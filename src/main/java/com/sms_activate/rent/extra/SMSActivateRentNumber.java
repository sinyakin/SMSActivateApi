package com.sms_activate.rent.extra;

import org.jetbrains.annotations.NotNull;

public class SMSActivateRentNumber {
  /**
   * Id rent.
   */
  private final int id;

  /**
   * Phone number.
   */
  private final String phone;

  /**
   * Constructor rent with id and number.
   *
   * @param id    id rent.
   * @param phone phone number.
   */
  public SMSActivateRentNumber(int id, @NotNull String phone) {
    this.id = id;
    this.phone = phone;
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
  @NotNull
  public String getNumber() {
    return phone;
  }
}
