package com.sms_activate.arch.activation;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateActivation extends SMSActivateMainResponse {
  private final int id;
  private final String number;

  public SMSActivateActivation(@NotNull SMSActivateStatusResponse status, int id, @NotNull String number) {
    super(status);
    this.id = id;
    this.number = number;
  }

  public int getId() {
    return id;
  }

  public String getNumber() {
    return number;
  }
}
