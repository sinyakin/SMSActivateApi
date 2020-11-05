package com.sms_activate;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Country {
    /**
     *
     */
    private CountryInformation countryInformation;

    /**
     *
     */
    private List<ServiceCost> serviceCostList;

    /**
     *
     * @param countryInformation
     * @param serviceCostList
     */
    public Country(@NotNull CountryInformation countryInformation, @NotNull List<ServiceCost> serviceCostList) {
        this.countryInformation = countryInformation;
        this.serviceCostList = serviceCostList;
    }

    public List<ServiceCost> getServiceCostList() {
        return serviceCostList;
    }

    public CountryInformation getCountryInformation() {
        return countryInformation;
    }
}
