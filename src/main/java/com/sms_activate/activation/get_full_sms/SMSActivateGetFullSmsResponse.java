package com.sms_activate.activation.get_full_sms;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetFullSmsResponse {
  private final String text;
  private final SMSActivateGetFullTypeResponse smsActivateGetFullTypeResponse;

  public SMSActivateGetFullSmsResponse(@NotNull String text, @NotNull SMSActivateGetFullTypeResponse smsActivateGetFullTypeResponse) {
    this.text = text;
    this.smsActivateGetFullTypeResponse = smsActivateGetFullTypeResponse;
  }

  @NotNull
  public SMSActivateGetFullTypeResponse getSmsActivateGetFullTypeResponse() {
    return smsActivateGetFullTypeResponse;
  }

  @NotNull
  public String getText() {
    return text;
  }
}
