package com.sms_activate.activation.extra;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetPriceInfo {
  /**
   * Service cost.
   */
  private BigDecimal cost;
  /**
   * count phone numbers.
   */
  private int count;

  private SMSActivateGetPriceInfo() {
  }

  /**
   * Returns the service cost in ruble.
   *
   * @return service cost in ruble.
   */
  @NotNull
  public BigDecimal getCost() {
    return cost;
  }

  /**
   * Returns the count phone numbers.
   *
   * @return count phone numbers.
   */
  public int getCount() {
    return count;
  }
}
