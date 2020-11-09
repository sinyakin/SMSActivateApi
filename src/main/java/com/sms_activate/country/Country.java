package com.sms_activate.country;

import com.sms_activate.service.ServiceCost;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Country {
    /**
     * Information about country.
     */
    private final CountryInformation countryInformation;

    /**
     * List services in country.
     */
    private final List<ServiceCost> serviceCostList;

    /**
     * Constructor country with information and list service.
     * @param countryInformation information about country.
     * @param serviceCostList list services in country.
     */
    public Country(@NotNull CountryInformation countryInformation, @NotNull List<ServiceCost> serviceCostList) {
        this.countryInformation = countryInformation;
        this.serviceCostList = serviceCostList;
    }

    /**
     * Returns the list services.
     * @return list services.
     */
    public List<ServiceCost> getServiceCostList() {
        return serviceCostList;
    }

    /**
     * Returns the information about country.
     * @return information about country.
     */
    public CountryInformation getCountryInformation() {
        return countryInformation;
    }
}
