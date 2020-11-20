package com.sms_activate.activation.numbers_status;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetNumberStatusResponse {
  private final boolean forward;
  private final int countNumber;
  private final String nameService;

  public SMSActivateGetNumberStatusResponse(
      boolean forward,
      int countNumber,
      @NotNull String nameService
  ) {
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
