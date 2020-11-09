package com.sms_activate.qiwi;

import org.jetbrains.annotations.NotNull;

public class QiwiResponse {
    /**
     * Status qiwi wallet.
     */
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
     * @param qiwiStatus status qiwi wallet (not be null).
     * @param walletNumber number qiwi wallet.
     * @param comment comment specified in payment.
     */
    public QiwiResponse(@NotNull QiwiStatus qiwiStatus, String walletNumber, String comment) {
        this.qiwiStatus = qiwiStatus;
        this.walletNumber = walletNumber;
        this.comment = comment;
    }

    /**
     * Returns the qiwi status.
     * @return qiwi status.
     */
    public QiwiStatus getQiwiStatus() {
        return qiwiStatus;
    }

    /**
     * Sets the qiwi status
     * @param qiwiStatus qiwi status
     */
    public void setQiwiStatus(QiwiStatus qiwiStatus) {
        this.qiwiStatus = qiwiStatus;
    }

    /**
     * Returns the wallet number
     * @return wallet number
     */
    public String getWalletNumber() {
        return walletNumber;
    }

    /**
     * Sets the wallet number.
     * @param walletNumber wallet number.
     */
    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    /**
     * Returns the comment specified payment.
     * @return comment specified payment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment specified payment
     * @param comment comment specified payment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
