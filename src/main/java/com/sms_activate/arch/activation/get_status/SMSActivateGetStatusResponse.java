package com.sms_activate.arch.activation.get_status;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMSActivateGetStatusResponse extends SMSActivateMainResponse {
  private final SMSActivateGetStatus smsActivateGetStatus;

  /**
   * Code from sms.
   */
  private final String codeFromSMS;

  public SMSActivateGetStatusResponse(@NotNull SMSActivateGetStatus smsActivateGetStatus, @Nullable String codeFromSMS) {
    super(SMSActivateMainStatusResponse.SUCCESS);
    this.smsActivateGetStatus = smsActivateGetStatus;
    this.codeFromSMS = codeFromSMS;
  }

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

  @NotNull
  public SMSActivateGetStatus getSMSActivateGetStatus() {
    return smsActivateGetStatus;
  }
}
