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
  private final SMSActivateStatusNumber smsActivateStatusNumber;

  /**
   * Constructor response getFullSMS with data from server.
   *
   * @param text                    text sms.
   * @param smsActivateStatusNumber status activation.
   */
  public SMSActivateGetFullSmsResponse(@NotNull String text, @NotNull SMSActivateStatusNumber smsActivateStatusNumber) {
    this.text = text;
    this.smsActivateStatusNumber = smsActivateStatusNumber;
  }

  /**
   * Returns the status activation.
   *
   * @return status activation.
   */
  @NotNull
  public SMSActivateStatusNumber getSmsActivateGetFullTypeResponse() {
    return smsActivateStatusNumber;
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
