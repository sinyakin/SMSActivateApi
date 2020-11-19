package com.sms_activate.arch.rent.get_rent_services_and_countries;

import com.sms_activate.arch.SMSActivateMainResponse;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateRentService {
  private final String name;
  private final BigDecimal cost;
  private final int count;

  public SMSActivateRentService(@NotNull String name, @NotNull BigDecimal cost, int count) {
    this.name = name;
    this.cost = cost;
    this.count = count;
  }

  @NotNull
  public String getName() {
    return name;
  }

  @NotNull
  public BigDecimal getCost() {
    return cost;
  }

  public int getCount() {
    return count;
  }
}
