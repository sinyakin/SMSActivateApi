package com.sms_activate.phone;

import com.sms_activate.service.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PhoneRent extends Phone {
  /**
   * End date of rent
   */
  private String endDate;

  /**
   * Constructor of phone rent.
   *
   * @param number  number phone (not be null).
   * @param id      id operation.
   * @param forward forward phone number (not be null).
   * @param service service for phone number (not be null).
   * @param endDate end date of rent (not be null).
   */
  public PhoneRent(
      @NotNull String number,
      @Nullable Service service,
      int id,
      boolean forward,
      @NotNull String endDate
  ) {
    super(number, service, id, forward);
    this.endDate = endDate;
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
