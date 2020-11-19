package com.sms_activate.arch.activation.country;

import com.sms_activate.arch.SMSActivateMainResponse;
import com.sms_activate.arch.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateGetCountryResponse extends SMSActivateMainResponse {
  private final int id;

  private final String chineseMessage;
  private final String englishMessage;
  private final String russianMessage;

  private final boolean supportRent;
  private final boolean supportRetry;
  private final boolean supportVisible;
  private final boolean supportMultiService;

  public SMSActivateGetCountryResponse(
      int id,
      @NotNull String chineseMessage,
      @NotNull String englishMessage,
      @NotNull String russianMessage,
      boolean supportRent,
      boolean supportRetry,
      boolean supportVisible,
      boolean supportMultiService
  ) {
    super(SMSActivateMainStatusResponse.SUCCESS);
    this.id = id;
    this.chineseMessage = chineseMessage;
    this.englishMessage = englishMessage;
    this.russianMessage = russianMessage;

    this.supportRent = supportRent;
    this.supportRetry = supportRetry;
    this.supportVisible = supportVisible;
    this.supportMultiService = supportMultiService;
  }

  public int getId() {
    return id;
  }

  @NotNull
  public String getChineseMessage() {
    return chineseMessage;
  }

  @NotNull
  public String getEnglishMessage() {
    return englishMessage;
  }

  @NotNull
  public String getRussianMessage() {
    return russianMessage;
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
