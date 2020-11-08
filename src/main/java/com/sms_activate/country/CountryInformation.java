package com.sms_activate.country;

import org.jetbrains.annotations.NotNull;

public class CountryInformation {
    private String russianName;
    private String englandName;
    private String chineName;
    private int id;
    private boolean visible;
    private boolean supportRetry;
    private boolean supportRent;
    private boolean supportMultiService;

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
        this.id = Math.round(id);
        this.visible = visible;
        this.supportRetry = supportRetry;
        this.supportRent = supportRent;
        this.supportMultiService = supportMultiService;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getEnglandName() {
        return englandName;
    }

    public String getChineName() {
        return chineName;
    }

    public int getId() {
        return id;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isSupportRetry() {
        return supportRetry;
    }

    public boolean isSupportMultiService() {
        return supportMultiService;
    }
}
