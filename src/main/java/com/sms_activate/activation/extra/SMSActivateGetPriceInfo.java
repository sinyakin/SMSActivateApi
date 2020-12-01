package com.sms_activate.activation.extra;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetPriceInfo {
  /**
   * Name service.
   */
  private final String name;
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
   * @param name  service name.
   * @param cost  cost service.
   * @param count count number service.
   */
  public SMSActivateGetPriceInfo(@NotNull String name, @NotNull BigDecimal cost, int count) {
    this.name = name;
    this.cost = cost;
    this.count = count;
  }

  /**
   * Returns the name service.
   *
   * @return name service.
   */
  @NotNull
  public String getShortName() {
    return name;
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
