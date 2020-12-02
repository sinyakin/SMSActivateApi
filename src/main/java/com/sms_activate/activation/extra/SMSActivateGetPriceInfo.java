package com.sms_activate.activation.extra;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetPriceInfo {
  /**
   * Service cost.
   */
  private final BigDecimal cost;
  /**
   * Count number.
   */
  private final int count;

  /**
   * Constructor SMSActivateGetPriceResponse with name, cost, count.
   *
   * @param cost  cost service.
   * @param count count number service.
   */
  public SMSActivateGetPriceInfo(@NotNull BigDecimal cost, int count) {
    this.cost = cost;
    this.count = count;
  }

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
