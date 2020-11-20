package com.sms_activate.qiwi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMSActivateGetQiwiRequisitesResponse {
  private final SMSActivateQiwiStatus status;
  private final String comment;
  private final String wallet;
  private final String upToDate;

  public SMSActivateGetQiwiRequisitesResponse(
      @NotNull SMSActivateQiwiStatus status,
      @Nullable String comment,
      @Nullable String wallet,
      @Nullable String upToDate
  ) {
    this.status = status;
    this.comment = comment;
    this.wallet = wallet;
    this.upToDate = upToDate;
  }

  @Nullable
  public SMSActivateQiwiStatus getStatus() {
    return status;
  }

  @Nullable
  public String getComment() {
    return comment;
  }

  @Nullable
  public String getWallet() {
    return wallet;
  }

  @Nullable
  public String getUpToDate() {
    return upToDate;
  }
}