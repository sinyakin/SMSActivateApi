package com.sms_activate.activation.get_full_sms;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetFullSmsResponse {
  /**
   * Text sms.
   */
  private final String text;

  /**
   * Status activation.
   */
  private final SMSActivateGetFullTypeResponse smsActivateGetFullTypeResponse;

  /**
   * Constructor response getFullSMS with data..
   *
   * @param text                           text sms.
   * @param smsActivateGetFullTypeResponse status activation.
   */
  public SMSActivateGetFullSmsResponse(@NotNull String text, @NotNull SMSActivateGetFullTypeResponse smsActivateGetFullTypeResponse) {
    this.text = text;
    this.smsActivateGetFullTypeResponse = smsActivateGetFullTypeResponse;
  }

  /**
   * Returns the status activation.
   *
   * @return status activation.
   */
  @NotNull
  public SMSActivateGetFullTypeResponse getSmsActivateGetFullTypeResponse() {
    return smsActivateGetFullTypeResponse;
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
