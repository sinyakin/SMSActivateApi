package com.sms_activate.arch.activation.balance;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetBalanceAndCashBackResponse extends SMSActivateGetBalanceResponse {
  private final BigDecimal cashBack;

  public SMSActivateGetBalanceAndCashBackResponse(@NotNull BigDecimal balance, @NotNull BigDecimal cashBack) {
    super(balance);
    this.cashBack = cashBack;
  }

  @NotNull
  public BigDecimal getCashBack() {
    return cashBack;
  }

  @NotNull
  public BigDecimal getBalanceAndCashBack() {
    return getBalance().add(cashBack);
  }
}
