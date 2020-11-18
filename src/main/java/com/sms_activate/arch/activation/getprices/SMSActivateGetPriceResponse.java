package com.sms_activate.arch.activation.getprices;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetPriceResponse extends SMSActivateMainResponse {
  private final String name;
  private final BigDecimal cost;
  private final int count;

  public SMSActivateGetPriceResponse(@NotNull String name, @NotNull BigDecimal cost, int count) {
    super(SMSActivateStatusResponse.SUCCESS);
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
