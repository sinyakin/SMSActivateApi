package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetBalanceAndCashBackResponse {
  /**
   * Cashback in account
   */
  private final BigDecimal cashBack;

  /**
   * Balance in account.
   */
  private final BigDecimal balance;

  /**
   * Constructor getBalanceAndCashBack response with data.
   *
   * @param balance  balance in account.
   * @param cashBack cashback in account.
   */
  public SMSActivateGetBalanceAndCashBackResponse(@NotNull BigDecimal balance, @NotNull BigDecimal cashBack) {
    this.balance = balance;
    this.cashBack = cashBack;
  }

  /**
   * Returns the cashback in account.
   *
   * @return cashback in account
   */
  @NotNull
  public BigDecimal getCashBack() {
    return cashBack;
  }

  /**
   * Returns the balance in account.
   *
   * @return balance in account
   */
  @NotNull
  public BigDecimal getBalance() {
    return balance;
  }

  /**
   * Returns the sum with balance and cashback.
   *
   * @return sum with balance and cashback.
   */
  @NotNull
  public BigDecimal getBalanceAndCashBack() {
    return getBalance().add(cashBack);
  }
}
