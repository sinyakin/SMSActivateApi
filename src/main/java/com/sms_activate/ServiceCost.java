package com.sms_activate;

import org.jetbrains.annotations.NotNull;

public class ServiceCost extends Service {
    private double cost;

    public ServiceCost(@NotNull String fullName, @NotNull String shortName, int countNumber, double cost) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.countNumber = Math.round(countNumber);
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
