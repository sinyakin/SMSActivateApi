package com.sms_activate.arch;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetCountriesResponse extends SMSActivateMainResponse {
  private final String chn;
  private final String eng;
  private final String rus;

  private final boolean retry;
  private final boolean visible;
  private final boolean multiService;

  public SMSActivateGetCountriesResponse(
      int id,
      @NotNull String chn,
      @NotNull String eng,
      @NotNull String rus,
      boolean retry,
      boolean visible,
      boolean multiService
  ) {
    super(id);
    this.chn = chn;
    this.eng = eng;
    this.rus = rus;

    this.visible = visible;
    this.retry = retry;
    this.multiService = multiService;
  }

  @NotNull
  public String getChn() {
    return chn;
  }

  @NotNull
  public String getEng() {
    return eng;
  }

  @NotNull
  public String getRus() {
    return rus;
  }

  public boolean isRetry() {
    return retry;
  }

  public boolean isVisible() {
    return visible;
  }

  public boolean isMultiService() {
    return multiService;
  }
}
