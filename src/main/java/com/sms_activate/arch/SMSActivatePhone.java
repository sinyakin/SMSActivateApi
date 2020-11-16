package com.sms_activate.arch;

import org.jetbrains.annotations.NotNull;

public class SMSActivatePhone {
  private final String number;

  public SMSActivatePhone(@NotNull String number) {
    this.number = number;
  }

  @NotNull
  public String getNumber() {
    return number;
  }
}
