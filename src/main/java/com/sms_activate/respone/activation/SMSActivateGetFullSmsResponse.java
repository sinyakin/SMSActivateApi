package com.sms_activate.respone.activation;

import com.sms_activate.respone.activation.extra.SMSActivateStatusNumber;
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
  public SMSActivateGetFullSmsResponse(@NotNull SMSActivateStatusNumber smsActivateStatusNumber, @NotNull String text) {
    this.smsActivateStatusNumber = smsActivateStatusNumber;
    this.text = text;
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
