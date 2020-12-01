package com.sms_activate.activation.extra;

import org.jetbrains.annotations.NotNull;

public class SMSActivateCountryInfo {
  /**
   * Country id.
   */
  private final int id;

  /**
   * Country name on chinese.
   */
  private final String chineseName;

  /**
   * Country name on english.
   */
  private final String englishName;

  /**
   * Country name on russian.
   */
  private final String russianName;

  /**
   * Country support rent.
   */
  private final boolean supportRent;

  /**
   * Country support retry sms.
   */
  private final boolean supportRetry;

  /**
   * Country visible in site.
   */
  private final boolean visible;

  /**
   * Country support multi service.
   */
  private final boolean supportMultiService;

  /**
   * Constructor sms-activate country.
   *
   * @param id                  country id.
   * @param russianName         name on chinese.
   * @param englishName         name on english.
   * @param chineseName         name on russian.
   * @param supportRent         support rent.
   * @param supportRetry        support retry sms.
   * @param visible             in site.
   * @param supportMultiService support multi service.
   */
  public SMSActivateCountryInfo(
    int id,
    @NotNull String russianName,
    @NotNull String englishName,
    @NotNull String chineseName,
    boolean supportRent,
    boolean supportRetry,
    boolean visible,
    boolean supportMultiService
  ) {
    this.id = id;
    this.chineseName = chineseName;
    this.englishName = englishName;
    this.russianName = russianName;

    this.supportRent = supportRent;
    this.supportRetry = supportRetry;
    this.visible = visible;
    this.supportMultiService = supportMultiService;
  }

  /**
   * Returns the country id.
   *
   * @return country id.
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the name on chinese.
   *
   * @return name on chinese.
   */
  @NotNull
  public String getChineseName() {
    return chineseName;
  }

  /**
   * Returns the name on english.
   *
   * @return name on english.
   */
  @NotNull
  public String getEnglishName() {
    return englishName;
  }

  /**
   * Returns the name on russian.
   *
   * @return name on russian.
   */
  @NotNull
  public String getRussianName() {
    return russianName;
  }

  /**
   * Returns the support rent.
   *
   * @return support rent.
   */
  public boolean isSupportRent() {
    return supportRent;
  }

  /**
   * Returns the support retry sms.
   *
   * @return support retry sms.
   */
  public boolean isSupportRetry() {
    return supportRetry;
  }

  /**
   * Returns the visible in site.
   *
   * @return visible in site.
   */
  public boolean isVisible() {
    return visible;
  }

  /**
   * Returns the support multi service.
   *
   * @return support multi service.
   */
  public boolean isSupportMultiService() {
    return supportMultiService;
  }
}
