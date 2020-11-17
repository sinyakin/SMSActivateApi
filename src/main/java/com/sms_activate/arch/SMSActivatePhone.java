package com.sms_activate.arch;

import org.jetbrains.annotations.NotNull;

public class SMSActivatePhone extends SMSActivateMainResponse {
  private final String number;

  public SMSActivatePhone(int id, @NotNull String number) {
    super(id);
    this.number = number;
  }

  @NotNull
  public String getNumber() {
    return number;
  }
}
