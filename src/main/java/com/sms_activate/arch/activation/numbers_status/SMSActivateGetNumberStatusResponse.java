package com.sms_activate.arch.activation.numbers_status;

import com.sms_activate.arch.main_response.SMSActivateMainResponse;
import com.sms_activate.arch.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateGetNumberStatusResponse extends SMSActivateMainResponse {
  private final boolean forward;
  private final int countNumber;
  private final String nameService;

  public SMSActivateGetNumberStatusResponse(
      boolean forward,
      int countNumber,
      @NotNull String nameService
  ) {
    super(SMSActivateMainStatusResponse.SUCCESS);
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
