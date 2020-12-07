package com.sms_activate.respone.activation.set_status;

import org.jetbrains.annotations.NotNull;

public class SMSActivateSetStatusResponse {
  /**
   * Status about activation from server.
   */
  private final SMSActivateServerStatus smsActivateServerStatus;

  /**
   * Constructor response setStatus with specified status from server.
   *
   * @param smsActivateServerStatus status about activation from server.
   */
  public SMSActivateSetStatusResponse(@NotNull SMSActivateServerStatus smsActivateServerStatus) {
    this.smsActivateServerStatus = smsActivateServerStatus;
  }

  /**
   * Returns the description about status activation.
   *
   * @return description about status activation.
   */
  @NotNull
  public String getMessage() {
    return smsActivateServerStatus.getMessage();
  }

  /**
   * Returns the status activation.
   *
   * @return status activation.
   */
  @NotNull
  public SMSActivateServerStatus getSMSActivateAccessStatus() {
    return smsActivateServerStatus;
  }
}
