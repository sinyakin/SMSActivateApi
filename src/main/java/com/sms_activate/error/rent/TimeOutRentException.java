package com.sms_activate.error.rent;

import org.jetbrains.annotations.NotNull;

public class TimeOutRentException extends RentException {
  public TimeOutRentException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
