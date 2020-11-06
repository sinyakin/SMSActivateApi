package com.sms_activate.service;

import org.jetbrains.annotations.NotNull;

public class ServiceForward extends Service {
    private final boolean isForward;

    public ServiceForward(@NotNull String fullName, @NotNull String shortName, int countNumber, boolean isForward) {
        super(fullName, shortName, Math.round(countNumber));
        this.isForward = isForward;
    }

    public boolean isForward() {
        return isForward;
    }
}
