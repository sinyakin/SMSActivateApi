package com.sms_activate.activation.balance;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetBalanceResponse {
  /**
   * Balance in account.
   */
  private final BigDecimal balance;

  /**
   * Constructor get balance response.
   * @param balance balance in account.
   */
  public SMSActivateGetBalanceResponse(@NotNull BigDecimal balance) {
    this.balance = balance;
  }

  /**
   * Returns the balance in account.
   * @return balance in account
   */
  @NotNull
  public BigDecimal getBalance() {
    return balance;
  }
}
