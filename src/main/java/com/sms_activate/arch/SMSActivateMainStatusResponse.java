package com.sms_activate.arch;

import org.jetbrains.annotations.NotNull;

public enum SMSActivateMainStatusResponse {
  UNKNOWN("", "", ""),
  SUCCESS("", "", ""),
  NO_BALANCE("", "", ""),
  NO_NUMBER("", "", ""),
  CANT_CANCEL("", "", ""),
  ALREADY_("", "", ""),
  ERROR("", "", ""),
  ;

  private final String englishMessage;
  private final String russianMessage;
  private final String response;

  SMSActivateMainStatusResponse(@NotNull String englishMessage, @NotNull String russianMessage, @NotNull String response) {
    this.englishMessage = englishMessage;
    this.russianMessage = russianMessage;
    this.response = response;
  }

  @NotNull
  public String getEnglishMessage() {
    return englishMessage;
  }

  @NotNull
  public String getRussianMessage() {
    return russianMessage;
  }

  @NotNull
  public String getMessage() {
    return String.join("/", englishMessage, russianMessage);
  }

  @NotNull
  public static SMSActivateMainStatusResponse getStatusByName(@NotNull String name) {
    name = name.toLowerCase();

    for (SMSActivateMainStatusResponse status : values()) {
      if (status.response.equals(name)) {
        return status;
      }
    }

    return UNKNOWN;
  }
}
