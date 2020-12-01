package com.sms_activate.activation.get_prices;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetPriceResponse {
  /**
   * Name service.
   */
  private final String service;
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
  public SMSActivateGetPriceResponse(@NotNull String name, @NotNull BigDecimal cost, int count) {
    this.service = name;
    this.cost = cost;
    this.count = count;
  }

  /**
   * Returns the name service.
   *
   * @return name service.
   */
  @NotNull
  public String getName() {
    return service;
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
