package com.sms_activate;

import org.jetbrains.annotations.NotNull;

public enum Action {
    ;
    private final String name;

    Action(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
