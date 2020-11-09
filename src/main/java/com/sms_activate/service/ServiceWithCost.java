package com.sms_activate.service;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class ServiceWithCost extends Service {
    /**
     * Cost of service.
     */
    private final BigDecimal cost;

    /**
     * Constructor service with cost, short name of service and count number in service.
     *
     * @param shortName   short name of service.
     * @param countNumber count number in service.
     * @param cost        cost of service.
     */
    public ServiceWithCost(@NotNull String shortName, int countNumber, @NotNull BigDecimal cost) {
        super(shortName, countNumber);
        this.cost = cost;
    }

    /**
     * Returns the cost of service.
     *
     * @return cost of service.
     */
    @NotNull
    public BigDecimal getCost() {
        return cost;
    }
}
