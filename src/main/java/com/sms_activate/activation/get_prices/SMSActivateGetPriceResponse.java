package com.sms_activate.activation.get_prices;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetPriceResponse {
  private final String nameService;
  private final BigDecimal cost;
  private final int count;

  public SMSActivateGetPriceResponse(@NotNull String name, @NotNull BigDecimal cost, int count) {
    this.nameService = name;
    this.cost = cost;
    this.count = count;
  }

  @NotNull
  public String getName() {
    return nameService;
  }

  @NotNull
  public BigDecimal getCost() {
    return cost;
  }

  public int getCount() {
    return count;
  }
}
