package com.sms_activate;

import org.jetbrains.annotations.NotNull;

public class ServiceForward extends Service {
    private final boolean isForward;

    public ServiceForward(@NotNull String fullName, @NotNull String shortName, int countNumber, boolean isForward) {
        this.isForward = isForward;
        this.fullName = fullName;
        this.shortName = shortName;
        this.countNumber = countNumber;
    }

    public boolean isForward() {
        return isForward;
    }
}
