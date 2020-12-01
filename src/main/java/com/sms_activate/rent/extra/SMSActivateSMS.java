package com.sms_activate.rent.extra;

import org.jetbrains.annotations.NotNull;

public class SMSActivateSMS {
  /**
   * Phone where the sms came from.
   */
  private final String phoneFrom;

  /**
   * Text sms.
   */
  private final String text;

  /**
   * SMS arrival date.
   */
  private final String date;

  /**
   * Service short name.
   */
  private final String service;

  /**
   * Constructor sms with phone, text, date.
   *
   * @param phoneFrom phone from such a sms came (not be null).
   * @param text      text sms (not be null).
   * @param date      sms arrival date (not be null).
   * @param service   short service name (not be null).
   */
  public SMSActivateSMS(
    @NotNull String phoneFrom,
    @NotNull String text,
    @NotNull String date,
    @NotNull String service
  ) {
    this.phoneFrom = phoneFrom;
    this.text = text;
    this.date = date;
    this.service = service;
  }

  /**
   * Returns the phone from such a sms came.
   *
   * @return phone from such a sms came.
   */
  @NotNull
  public String getPhoneFrom() {
    return phoneFrom;
  }

  /**
   * Returns the text sms.
   *
   * @return text sms.
   */
  @NotNull
  public String getText() {
    return text;
  }

  /**
   * Returns the sms arrival date.
   *
   * @return sms arrival date.
   */
  @NotNull
  public String getDate() {
    return date;
  }

  /**
   * Returns the short service name.
   *
   * @return short service name.
   */
  @NotNull
  public String getService() {
    return service;
  }
}
