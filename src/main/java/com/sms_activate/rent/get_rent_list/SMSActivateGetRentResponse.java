package com.sms_activate.rent.get_rent_list;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetRentResponse {
  private final int id;
  private final String number;

  public SMSActivateGetRentResponse(int id, @NotNull String number) {
    this.id = id;
    this.number = number;
  }

  public int getId() {
    return id;
  }

  @NotNull
  public String getNumber() {
    return number;
  }
}
