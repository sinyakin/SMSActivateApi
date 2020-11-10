package com.sms_activate;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class Balance {
  /**
   *
   */
  private BigDecimal balance = BigDecimal.ZERO;

  /**
   *
   */
  private BigDecimal cashBack = BigDecimal.ZERO;

  /**
   * Constructor the balance with
   * @param balance
   */
  public Balance(@NotNull BigDecimal balance) {
    this.balance = balance;
  }

  /**
   * Constructor the balance with
   * @param balance
   * @param cashBack
   */
  public Balance(@NotNull BigDecimal balance, @NotNull BigDecimal cashBack) {
    this.balance = balance;
    this.cashBack = cashBack;
  }

  /**
   * Returns the balance.
   * @return balance.
   */
  @NotNull
  public BigDecimal getBalance() {
    return balance;
  }

  /**
   * Returns the cashback.
   * @return cashback.
   */
  @NotNull
  public BigDecimal getCashBack() {
    return cashBack;
  }
}
