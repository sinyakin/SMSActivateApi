package com.sms_activate.country;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

public class CountryInformation {
    private String russianName;
    private String englandName;
    private String chineName;
    private int id;
    private boolean isVisible;
    private boolean isSupportRetry;
    private boolean isSupportRent;
    private boolean isSupportMultiService;

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
        boolean isVisible,
        boolean isSupportRetry,
        boolean isSupportRent,
        boolean isSupportMultiService
    ) {
        this.russianName = russianName;
        this.englandName = englandName;
        this.chineName = chineName;
        this.id = Math.round(id);
        this.isVisible = isVisible;
        this.isSupportRetry = isSupportRetry;
        this.isSupportRent = isSupportRent;
        this.isSupportMultiService = isSupportMultiService;
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
        return isVisible;
    }

    public boolean isSupportRetry() {
        return isSupportRetry;
    }

    public boolean isSupportMultiService() {
        return isSupportMultiService;
    }
}
