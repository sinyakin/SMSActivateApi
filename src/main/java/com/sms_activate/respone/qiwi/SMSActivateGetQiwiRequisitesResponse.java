package com.sms_activate.respone.qiwi;

import com.sms_activate.error.SMSActivateUnknownException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMSActivateGetQiwiRequisitesResponse {
  /**
   * Status qiwi wallet.
   */
  private String status;

  /**
   * Comment indicated when paying.
   */
  private String comment;

  /**
   * Number qiwi wallet.
   */
  private long wallet;

  /**
   * Date by which the details are relevant.
   */
  private String upToDate;

  private SMSActivateGetQiwiRequisitesResponse() {

  }

  /**
   * Returns the status qiwi wallet.
   *
   * @return status qiwi wallet.
   */
  @NotNull
  public SMSActivateQiwiStatus getStatus() throws SMSActivateUnknownException {
    SMSActivateQiwiStatus status = SMSActivateQiwiStatus.getStatusByName(this.status);
    if (status == SMSActivateQiwiStatus.UNKNOWN) {
      throw new SMSActivateUnknownException(this.status, "Unknown status of qiwi wallet.");
    }

    return status;
  }

  /**
   * Returns the comment indicated when paying.
   *
   * @return comment indicated when paying.
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
  public long getWallet() {
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