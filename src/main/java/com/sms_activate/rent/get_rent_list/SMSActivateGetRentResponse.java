package com.sms_activate.rent.get_rent_list;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetRentResponse {
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
  public SMSActivateGetRentResponse(int id, @NotNull String number) {
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
