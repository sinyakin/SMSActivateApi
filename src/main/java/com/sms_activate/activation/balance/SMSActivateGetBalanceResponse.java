package com.sms_activate.activation.balance;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetBalanceResponse {
  private final BigDecimal balance;

  public SMSActivateGetBalanceResponse(@NotNull BigDecimal balance) {
    this.balance = balance;
  }

  @NotNull
  public BigDecimal getBalance() {
    return balance;
  }
}
