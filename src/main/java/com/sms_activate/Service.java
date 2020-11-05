package com.sms_activate;

import org.jetbrains.annotations.NotNull;

public class Service {
    protected String fullName;
    protected String shortName;
    protected double countNumber;

    protected Service() {

    }

    public Service(@NotNull String fullName, @NotNull String shortName, double countNumber) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.countNumber = countNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public double getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }
}
