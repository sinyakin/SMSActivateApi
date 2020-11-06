package com.sms_activate.service;

import org.jetbrains.annotations.NotNull;

public class Service {
    private String fullName;
    private String shortName;
    private double countNumber;

    public Service(@NotNull String shortName) {
        this("", shortName);
    }

    public Service(@NotNull String fullName, @NotNull String shortName) {
        this(fullName, shortName, -1);
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
