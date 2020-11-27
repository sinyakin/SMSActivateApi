package com.sms_activate.qiwi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMSActivateGetQiwiRequisitesResponse {
  /**
   * Status qiwi wallet.
   */
  private final SMSActivateQiwiStatus status;

  /**
   * Comment specified in payment.
   */
  private final String comment;

  /**
   * Number qiwi wallet.
   */
  private final String wallet;

  /**
   * Date by which the details are relevant.
   */
  private final String upToDate;

  /**
   * Constructor the qiwi response with status, walletNumber.
   *
   * @param status   status qiwi wallet (not be null).
   * @param wallet   number qiwi wallet.
   * @param comment  comment specified in payment.
   * @param upToDate Date by which the details are relevant.
   */
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

  /**
   * Returns the status qiwi wallet.
   *
   * @return staus qiwi wallet.
   */
  @NotNull
  public SMSActivateQiwiStatus getStatus() {
    return status;
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