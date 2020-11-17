package com.sms_activate.arch.activation;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateGetNumbersStatusResponse extends SMSActivateMainResponse {
  private final boolean forward;
  private final int countNumber;
  private final String nameService;

  public SMSActivateGetNumbersStatusResponse(
      @NotNull SMSActivateStatusResponse status,
      boolean forward,
      int countNumber,
      @NotNull String nameService
  ) {
    super(status);
    this.forward = forward;
    this.countNumber = countNumber;
    this.nameService = nameService;
  }

  public boolean isForward() {
    return forward;
  }

  public int getCountNumber() {
    return countNumber;
  }

  @NotNull
  public String getNameService() {
    return nameService;
  }
}
