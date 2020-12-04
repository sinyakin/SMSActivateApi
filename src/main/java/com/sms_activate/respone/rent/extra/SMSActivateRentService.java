package com.sms_activate.respone.rent.extra;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateRentService {
  /**
   * Cost number by service (ruble).
   */
  private BigDecimal cost;

  /**
   * Count phone numbers in service.
   */
  private int count;

  private SMSActivateRentService() {
  }

  /**
   * Returns the cost number by service (ruble).
   *
   * @return cost number by service (ruble).
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
