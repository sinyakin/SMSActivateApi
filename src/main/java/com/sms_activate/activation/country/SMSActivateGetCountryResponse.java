package com.sms_activate.activation.country;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetCountryResponse {
  private final int id;

  private final String chineseName;
  private final String englishName;
  private final String russianName;

  private final boolean supportRent;
  private final boolean supportRetry;
  private final boolean supportVisible;
  private final boolean supportMultiService;

  public SMSActivateGetCountryResponse(
      int id,
      @NotNull String russianName,
      @NotNull String englishName,
      @NotNull String chineseName,
      boolean supportRent,
      boolean supportRetry,
      boolean supportVisible,
      boolean supportMultiService
  ) {
    this.id = id;
    this.chineseName = chineseName;
    this.englishName = englishName;
    this.russianName = russianName;

    this.supportRent = supportRent;
    this.supportRetry = supportRetry;
    this.supportVisible = supportVisible;
    this.supportMultiService = supportMultiService;
  }

  public int getId() {
    return id;
  }

  @NotNull
  public String getChineseName() {
    return chineseName;
  }

  @NotNull
  public String getEnglishName() {
    return englishName;
  }

  @NotNull
  public String getRussianName() {
    return russianName;
  }

  public boolean isSupportRent() {
    return supportRent;
  }

  public boolean isSupportRetry() {
    return supportRetry;
  }

  public boolean isSupportVisible() {
    return supportVisible;
  }

  public boolean isSupportMultiService() {
    return supportMultiService;
  }
}
