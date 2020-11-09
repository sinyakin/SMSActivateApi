package com.sms_activate.country;

import org.jetbrains.annotations.NotNull;

public class CountryInformation {
    /**
     * Name country in russian language.
     */
    private final String russianName;

    /**
     * Name country in england language.
     */
    private final String englandName;

    /**
     * Name country in chine language.
     */
    private final String chineName;

    /**
     * Id country
     */
    private final int id;

    /**
     * Country is visible in site.
     */
    private final boolean visible;

    /**
     * Country is support retry sms.
     */
    private final boolean supportRetry;

    /**
     * Country is support rent.
     */
    private final boolean supportRent;

    /**
     * Country is support multiservice.
     */
    private final boolean supportMultiService;

    /**
     * Constructor country with id.
     * @param id country id.
     */
    public CountryInformation(int id) {
        this(
            id,
            "",
            "",
            "",
            false,
            false,
            false,
            false
        );
    }

    /**
     * Constructor country with id, multiLand, supports options.
     * @param id country id.
     * @param russianName – name in russian language.
     * @param englandName – name in england language.
     * @param chineName – name in chine language.
     * @param visible visible in site.
     * @param supportRetry support retry sms.
     * @param supportRent support rent.
     * @param supportMultiService support multiservice.
     */
    public CountryInformation(
        int id,
        @NotNull String russianName,
        @NotNull String englandName,
        @NotNull String chineName,
        boolean visible,
        boolean supportRetry,
        boolean supportRent,
        boolean supportMultiService
    ) {
        this.russianName = russianName;
        this.englandName = englandName;
        this.chineName = chineName;
        this.id = id;
        this.visible = visible;
        this.supportRetry = supportRetry;
        this.supportRent = supportRent;
        this.supportMultiService = supportMultiService;
    }

    /**
     * Returns the name in russian language.
     * @return name in russian language.
     */
    public String getRussianName() {
        return russianName;
    }

    /**
     * Returns the name in england language.
     * @return name in england language.
     */
    public String getEnglandName() {
        return englandName;
    }

    /**
     * Returns the name in chine language.
     * @return name in chine language.
     */
    public String getChineName() {
        return chineName;
    }

    /**
     * Returns the country id.
     * @return country id.
     */
    public int getId() {
        return id;
    }

    /**
     * Return true if country visible in site, else false.
     * @return visible country in site.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Returns true if country support retry sms, else false.
     * @return support retry sms.
     */
    public boolean isSupportRetry() {
        return supportRetry;
    }

    /**
     * Returns true if country support multiservice, else false.
     * @return support multiservice.
     */
    public boolean isSupportMultiService() {
        return supportMultiService;
    }

    /**
     * Returns true if country support multiservice, else false.
     * @return support multiservice.
     */
    public boolean isSupportRent() {
        return supportRent;
    }
}
