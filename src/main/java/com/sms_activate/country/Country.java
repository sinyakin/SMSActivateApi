package com.sms_activate.country;

import org.jetbrains.annotations.NotNull;

public class Country {
  /**
   * Name country in russian language.
   */
  private String russianName = "";

  /**
   * Name country in english language.
   */
  private String englishName = "";

  /**
   * Name country in chine language.
   */
  private String chineseName = "";

  /**
   * Id country
   */
  private final int id;

  /**
   * Country is visible in site.
   */
  private boolean visible = false;

  /**
   * Country is support retry sms.
   */
  private boolean supportRetry = false;

  /**
   * Country is support rent.
   */
  private boolean supportRent = false;

  /**
   * Country is support multiservice.
   */
  private boolean supportMultiService = false;

  /**
   * Constructor country with id.
   *
   * @param id country id.
   */
  public Country(int id) {
    this.id = id;
  }

  /**
   * Constructor country with id, multiLand, supports options.
   *
   * @param id                  country id.
   * @param russianName         – name in russian language.
   * @param englishName         – name in english language.
   * @param chineseName           – name in chine language.
   * @param visible             visible in site.
   * @param supportRetry        support retry sms.
   * @param supportRent         support rent.
   * @param supportMultiService support multiservice.
   */
  public Country(
      int id,
      @NotNull String russianName,
      @NotNull String englishName,
      @NotNull String chineseName,
      boolean visible,
      boolean supportRetry,
      boolean supportRent,
      boolean supportMultiService
  ) {
    this.id = id;
    this.russianName = russianName;
    this.englishName = englishName;
    this.chineseName = chineseName;
    this.visible = visible;
    this.supportRetry = supportRetry;
    this.supportRent = supportRent;
    this.supportMultiService = supportMultiService;
  }

  /**
   * Returns the name in russian language.
   *
   * @return name in russian language.
   */
  @NotNull
  public String getRussianName() {
    return russianName;
  }

  /**
   * Returns the name in english language.
   *
   * @return name in english language.
   */
  @NotNull
  public String getEnglishName() {
    return englishName;
  }

  /**
   * Returns the name in chine language.
   *
   * @return name in chine language.
   */
  @NotNull
  public String getChineseName() {
    return chineseName;
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
   * Return true if country visible in site, else false.
   *
   * @return visible country in site.
   */
  public boolean isVisible() {
    return visible;
  }

  /**
   * Returns true if country support retry sms, else false.
   *
   * @return support retry sms.
   */
  public boolean isSupportRetry() {
    return supportRetry;
  }

  /**
   * Returns true if country support multiservice, else false.
   *
   * @return support multiservice.
   */
  public boolean isSupportMultiService() {
    return supportMultiService;
  }

  /**
   * Returns true if country support multiservice, else false.
   *
   * @return support multiservice.
   */
  public boolean isSupportRent() {
    return supportRent;
  }
}
