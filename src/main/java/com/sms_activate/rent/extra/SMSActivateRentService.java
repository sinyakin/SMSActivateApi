package com.sms_activate.rent.extra;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateRentService {
  /**
   * Cost by service.
   */
  private BigDecimal cost;

  /**
   * count phone numbers in service.
   */
  private int count;

  private SMSActivateRentService() {
  }

  /**
   * Returns the cost number by service.
   *
   * @return cost number by service.
   */
  @NotNull
  public BigDecimal getCost() {
    return cost;
  }

  /**
   * Returns the count phone numbers in service.
   *
   * @return count phone numbers in service.
   */
  public int getCount() {
    return count;
  }
}
