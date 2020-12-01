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
  private final String number;

  /**
   * Constructor rent with id and number.
   *
   * @param id     id rent.
   * @param number phone number.
   */
  public SMSActivateRentNumber(int id, @NotNull String number) {
    this.id = id;
    this.number = number;
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
    return number;
  }
}
