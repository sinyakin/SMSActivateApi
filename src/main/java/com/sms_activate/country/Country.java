package com.sms_activate.country;

import com.sms_activate.service.ServiceCost;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Country {
    /**
     *
     */
    private final CountryInformation countryInformation;

    /**
     *
     */
    private final List<ServiceCost> serviceCostList;

    /**
     *
     * @param countryInformation
     * @param serviceCostList
     */
    public Country(@NotNull CountryInformation countryInformation, @NotNull List<ServiceCost> serviceCostList) {
        this.countryInformation = countryInformation;
        this.serviceCostList = serviceCostList;
    }

    /**
     *
     * @return
     */
    public List<ServiceCost> getServiceCostList() {
        return serviceCostList;
    }

    /**
     *
     * @return
     */
    public CountryInformation getCountryInformation() {
        return countryInformation;
    }
}
