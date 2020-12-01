package com.sms_activate.rent.set_rent_status;

import com.sms_activate.rent.extra.SMSActivateRentStatus;
import org.jetbrains.annotations.NotNull;

public class SMSActivateSetRentStatusResponse {
  /**
   * Response about setRentStatus.
   */
  private final SMSActivateRentStatus status;

  /**
   * Constructor response setRentStatus.
   *
   * @param status response about status rent.
   */
  public SMSActivateSetRentStatusResponse(@NotNull SMSActivateRentStatus status) {
    this.status = status;
  }

  /**
   * Returns the description about status.
   *
   * @return description about status.
   */
  @NotNull
  public String getMessage() {
    return status.getMessage();
  }

  /**
   * Returns the response about status rent.
   *
   * @return response about status rent.
   */
  @NotNull
  public SMSActivateRentStatus getStatus() {
    return status;
  }
}
