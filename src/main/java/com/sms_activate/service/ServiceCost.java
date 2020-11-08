package com.sms_activate.service;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class ServiceCost extends Service {
    private final BigDecimal cost;

    public ServiceCost(@NotNull String fullName, @NotNull String shortName, int countNumber, @NotNull BigDecimal cost) {
        super(fullName, shortName, Math.round(countNumber));
        this.cost = cost;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
