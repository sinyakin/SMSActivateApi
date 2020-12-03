package com.sms_activate;

import org.jetbrains.annotations.NotNull;

class SMSActivateResponse {
  /**
   * Status request.
   */
  private String status;

  /**
   * Message about error.
   */
  private String msg;

  /**
   * Returns the error status.
   *
   * @return the error status.
   */
  @NotNull
  public String getStatus() {
    return status;
  }

  /**
   * Returns the message about error.
   *
   * @return message about error.
   */
  @NotNull
  public String getMessage() {
    return msg;
  }
}
