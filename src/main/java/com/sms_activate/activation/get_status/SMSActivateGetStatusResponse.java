package com.sms_activate.activation.get_status;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMSActivateGetStatusResponse {
  /**
   * Status activation.
   */
  private final SMSActivateGetStatus smsActivateGetStatus;

  /**
   * Code from sms.
   */
  private final String codeFromSMS;

  /**
   * Constructor response getStatus with data from server.
   *
   * @param smsActivateGetStatus status activation.
   * @param codeFromSMS          code sms.
   */
  public SMSActivateGetStatusResponse(@NotNull SMSActivateGetStatus smsActivateGetStatus, @Nullable String codeFromSMS) {
    this.smsActivateGetStatus = smsActivateGetStatus;
    this.codeFromSMS = codeFromSMS;
  }

  /**
   * Returns the description about status.
   *
   * @return description about status.
   */
  @NotNull
  public String getMessage() {
    return smsActivateGetStatus.getMessage();
  }

  /**
   * Returns the code from sms.
   *
   * @return code from sms.
   */
  @Nullable
  public String getCodeFromSMS() {
    return codeFromSMS;
  }

  /**
   * Returns the status activation.
   *
   * @return status activation.
   */
  @NotNull
  public SMSActivateGetStatus getSMSActivateGetStatus() {
    return smsActivateGetStatus;
  }
}
