package com.sms_activate.arch.qiwi;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetQiwiRequisitesResponse {
  private final SMSActivateQiwiStatus status;
  private final String comment;
  private final String wallet;
  private final String upToDate;

  public SMSActivateGetQiwiRequisitesResponse(
      @NotNull SMSActivateQiwiStatus status,
      @NotNull String comment,
      @NotNull String wallet,
      @NotNull String upToDate
  ) {
    this.status = status;
    this.comment = comment;
    this.wallet = wallet;
    this.upToDate = upToDate;
  }

  public SMSActivateQiwiStatus getStatus() {
    return status;
  }

  public String getComment() {
    return comment;
  }

  public String getWallet() {
    return wallet;
  }

  public String getUpToDate() {
    return upToDate;
  }
}