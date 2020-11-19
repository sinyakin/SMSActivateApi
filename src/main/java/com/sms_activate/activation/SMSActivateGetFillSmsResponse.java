package com.sms_activate.activation;

import com.sms_activate.main_response.SMSActivateMainResponse;
import com.sms_activate.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateGetFillSmsResponse {
  private final String text;

  public SMSActivateGetFillSmsResponse(@NotNull String text) {
    this.text = text;
  }

  @NotNull
  public String getText() {
    return text;
  }
}
