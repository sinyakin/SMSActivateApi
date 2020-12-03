package com.sms_activate.activation.extra;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetPriceInfo {
  /**
   * Service cost.
   */
  private BigDecimal cost;
  /**
   * Count number.
   */
  private int count;

  /**
   * Returns the service cost.
   *
   * @return service cost.
   */
  @NotNull
  public BigDecimal getCost() {
    return cost;
  }

  /**
   * Returns the count number.
   *
   * @return count number.
   */
  public int getCount() {
    return count;
  }
}
