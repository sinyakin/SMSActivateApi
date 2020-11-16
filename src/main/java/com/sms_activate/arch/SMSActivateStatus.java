package com.sms_activate.arch;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum SMSActivateStatus {
  SUCCESS("", "", ""),
  ERROR("", "", ""),
  ;

  private final String eng;
  private final String ru;
  private final String response;

  SMSActivateStatus(@NotNull String eng, @NotNull String ru, @NotNull String response) {
    this.eng = eng;
    this.ru = ru;
    this.response = response;
  }

  @NotNull
  public String getEng() {
    return eng;
  }

  @NotNull
  public String getRu() {
    return ru;
  }

  @NotNull
  public static SMSActivateStatus getStatusByName(@NotNull String name) {
    return SUCCESS;
//    name = name.toLowerCase();
//
//    for (SMSActivateStatus status : values()) {
//      if (name.equals(status.response)) {
//        return status;
//      }
//    }
//
//    return UNKNOWN;
  }
}
