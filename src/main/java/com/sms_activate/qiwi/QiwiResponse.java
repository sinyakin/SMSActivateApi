package com.sms_activate.qiwi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class QiwiResponse {
  /**
   * Status qiwi wallet.
   */
  //todo
  private QiwiStatus qiwiStatus;

  /**
   * Number qiwi wallet.
   */
  private String walletNumber;

  /**
   * Comment specified in payment.
   */
  private String comment;

  /**
   * Constructor the qiwi response with status, walletNumber.
   *
   * @param qiwiStatus   status qiwi wallet (not be null).
   * @param walletNumber number qiwi wallet.
   * @param comment      comment specified in payment.
   */
  public QiwiResponse(@NotNull QiwiStatus qiwiStatus, @Nullable String walletNumber, @Nullable String comment) {
    this.qiwiStatus = qiwiStatus;
    this.walletNumber = walletNumber;
    this.comment = comment;
  }

  /**
   * Returns the qiwi status.
   *
   * @return qiwi status.
   */
  @NotNull
  public QiwiStatus getQiwiStatus() {
    return qiwiStatus;
  }

  /**
   * Returns the wallet number
   *
   * @return wallet number
   */
  @Nullable
  public String getWalletNumber() {
    return walletNumber;
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
}
