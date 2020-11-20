package com.sms_activate.rent.get_rent_status;

import org.jetbrains.annotations.NotNull;

public class SMSActivateSMS {
  private final String phoneFrom;
  private final String text;
  private final String date;
  private final String service;

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

  @NotNull
  public String getPhoneFrom() {
    return phoneFrom;
  }

  @NotNull
  public String getText() {
    return text;
  }

  @NotNull
  public String getDate() {
    return date;
  }

  @NotNull
  public String getService() {
    return service;
  }
}
