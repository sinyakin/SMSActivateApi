package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetFullSmsResponse {
  private final String text;

  public SMSActivateGetFullSmsResponse(@NotNull String text) {
    this.text = text;
  }

  @NotNull
  public String getText() {
    return text;
  }
}
