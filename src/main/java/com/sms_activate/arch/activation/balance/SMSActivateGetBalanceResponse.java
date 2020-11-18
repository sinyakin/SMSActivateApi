package com.sms_activate.arch.activation.balance;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetBalanceResponse extends SMSActivateMainResponse {
  private final BigDecimal balance;

  public SMSActivateGetBalanceResponse(@NotNull BigDecimal balance) {
    super(SMSActivateStatusResponse.SUCCESS);
    this.balance = balance;
  }

  @NotNull
  public BigDecimal getBalance() {
    return balance;
  }
}
