package com.sms_activate.activation.balance;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetBalanceAndCashBackResponse extends SMSActivateGetBalanceResponse {
  /**
   * Cashback in account
   */
  private final BigDecimal cashBack;

  /**
   * Constructor getBalanceAndCashBack response with data.
   * @param balance balance in account.
   * @param cashBack cashback in account.
   */
  public SMSActivateGetBalanceAndCashBackResponse(@NotNull BigDecimal balance, @NotNull BigDecimal cashBack) {
    super(balance);
    this.cashBack = cashBack;
  }

  /**
   * Returns the cashback in account.
   * @return cashback in account
   */
  @NotNull
  public BigDecimal getCashBack() {
    return cashBack;
  }

  /**
   * Returns the sum with balance and cashback.
   * @return sum with balance and cashback.
   */
  @NotNull
  public BigDecimal getBalanceAndCashBack() {
    return getBalance().add(cashBack);
  }
}
