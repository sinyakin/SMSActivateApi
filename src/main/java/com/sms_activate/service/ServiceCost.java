package com.sms_activate.service;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class ServiceCost extends Service {
    /**
     * Cost of service.
     */
    private final BigDecimal cost;

    /**
     * Constructor service with cost, short name of service and count number in service.
     * @param shortName short name of service.
     * @param countNumber count number in service.
     * @param cost cost of service.
     */
    public ServiceCost(@NotNull String shortName, int countNumber, @NotNull BigDecimal cost) {
        super(shortName, Math.round(countNumber));
        this.cost = cost;
    }

    /**
     * Returns the cost of service.
     * @return cost of service.
     */
    public BigDecimal getCost() {
        return cost;
    }
}
