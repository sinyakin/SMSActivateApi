package com.sms_activate.activation.balance;

import com.sms_activate.main_response.SMSActivateMainResponse;
import com.sms_activate.main_response.SMSActivateMainStatusResponse;
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
