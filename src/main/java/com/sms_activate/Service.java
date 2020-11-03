package com.sms_activate;

public class Service {
    private String fullName;
    private String shortName;
    private final boolean forward;
    private int countNumbers;

    public Service(String fullName, String shortName, boolean forward, int countNumbers) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.forward = forward;
        this.countNumbers = countNumbers;
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

    public boolean isForward() {
        return forward;
    }

    public int getCountNumbers() {
        return countNumbers;
    }

    public void setCountNumbers(int countNumbers) {
        this.countNumbers = countNumbers;
    }
}
