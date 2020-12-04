package com.sms_activate.respone.activation.set_status;

import org.jetbrains.annotations.NotNull;

public class SMSActivateSetStatusResponse {
  /**
   * Response status about activation from server.
   */
  private final SMSActivateAccessStatus smsActivateAccessStatus;

  /**
   * Constructor response setStatus with specified status from server.
   *
   * @param smsActivateAccessStatus status about activation from server.
   */
  public SMSActivateSetStatusResponse(@NotNull SMSActivateAccessStatus smsActivateAccessStatus) {
    this.smsActivateAccessStatus = smsActivateAccessStatus;
  }

  /**
   * Returns the description about status.
   *
   * @return description about status.
   */
  @NotNull
  public String getMessage() {
    return smsActivateAccessStatus.getMessage();
  }

  /**
   * Returns the status about activation.
   *
   * @return status about activation.
   */
  @NotNull
  public SMSActivateAccessStatus getSMSActivateAccessStatus() {
    return smsActivateAccessStatus;
  }
}
