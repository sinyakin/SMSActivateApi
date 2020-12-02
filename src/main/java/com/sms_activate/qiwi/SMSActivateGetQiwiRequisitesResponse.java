package com.sms_activate.qiwi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMSActivateGetQiwiRequisitesResponse {
  /**
   * Status qiwi wallet.
   */
  private String status;

  /**
   * Comment specified in payment.
   */
  private String comment;

  /**
   * Number qiwi wallet.
   */
  private String wallet;

  /**
   * Date by which the details are relevant.
   */
  private String upToDate;

  /**
   * Returns the status qiwi wallet.
   *
   * @return staus qiwi wallet.
   */
  @NotNull
  public SMSActivateQiwiStatus getStatus() {
    return SMSActivateQiwiStatus.getStatusByName(status);
  }

  /**
   * Returns the comment specified payment.
   *
   * @return comment specified payment
   */
  @Nullable
  public String getComment() {
    return comment;
  }

  /**
   * Returns the wallet number.
   *
   * @return wallet number.
   */
  @Nullable
  public String getWallet() {
    return wallet;
  }

  /**
   * Returns the date by which the details are relevant.
   *
   * @return date by which the details are relevant.
   */
  @Nullable
  public String getUpToDate() {
    return upToDate;
  }
}