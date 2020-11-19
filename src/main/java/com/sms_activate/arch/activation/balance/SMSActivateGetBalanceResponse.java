package com.sms_activate.arch.activation.balance;

import com.sms_activate.arch.main_response.SMSActivateMainResponse;
import com.sms_activate.arch.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateGetBalanceResponse extends SMSActivateMainResponse {
  private final BigDecimal balance;

  public SMSActivateGetBalanceResponse(@NotNull BigDecimal balance) {
    super(SMSActivateMainStatusResponse.SUCCESS);
    this.balance = balance;
  }

  @NotNull
  public BigDecimal getBalance() {
    return balance;
  }
}
