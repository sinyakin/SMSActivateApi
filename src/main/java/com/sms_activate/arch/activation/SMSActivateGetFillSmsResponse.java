package com.sms_activate.arch.activation;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateGetFillSmsResponse extends SMSActivateMainResponse {
  private final String text;

  public SMSActivateGetFillSmsResponse(@NotNull String text) {
    super(SMSActivateMainStatusResponse.SUCCESS);
    this.text = text;
  }

  @NotNull
  public String getText() {
    return text;
  }
}
