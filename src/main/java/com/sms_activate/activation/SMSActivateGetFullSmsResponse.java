package com.sms_activate.activation;

import com.sms_activate.activation.extra.SMSActivateStatusNumber;
import org.jetbrains.annotations.NotNull;

public class SMSActivateGetFullSmsResponse {
  /**
   * Text sms.
   */
  private final String text;

  /**
   * Status activation.
   */
  private final com.sms_activate.activation.extra.SMSActivateStatusNumber SMSActivateStatusNumber;

  /**
   * Constructor response getFullSMS with data from server.
   *
   * @param text                    text sms.
   * @param SMSActivateStatusNumber status activation.
   */
  public SMSActivateGetFullSmsResponse(@NotNull String text, @NotNull SMSActivateStatusNumber SMSActivateStatusNumber) {
    this.text = text;
    this.SMSActivateStatusNumber = SMSActivateStatusNumber;
  }

  /**
   * Returns the status activation.
   *
   * @return status activation.
   */
  @NotNull
  public SMSActivateStatusNumber getSmsActivateGetFullTypeResponse() {
    return SMSActivateStatusNumber;
  }

  /**
   * Returns the text sms.
   *
   * @return text sms.
   */
  @NotNull
  public String getText() {
    return text;
  }
}
