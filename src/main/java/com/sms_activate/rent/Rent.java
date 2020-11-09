package com.sms_activate.rent;

import com.sms_activate.country.Country;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Rent {
    /**
     * Country current phone activation.
     */
    private final Country country;

    /**
     * List countries supports activation.
     */
    private final List<Integer> countryIdList;

    /**
     * List operator name.
     */
    private final List<String> operatorNameList;

    /**
     * Constructor rent with operatorNameList, countryList.
     * @param operatorNameList list name operator in country.
     * @param country country rent phone.
     * @param countryIdList list countries supports activation.
     */
    public Rent(@NotNull List<String> operatorNameList, @NotNull Country country, @NotNull List<Integer> countryIdList) {
       this.operatorNameList = operatorNameList;
       this.country = country;
       this.countryIdList = countryIdList;
    }

    /**
     * Returns the country rent phone.
     * @return country rent phone.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Returns the list countries supports activation.
     * @return list countries supports activation.
     */
    public List<Integer> getCountryIdList() {
        return countryIdList;
    }

    /**
     * Returns the list name operator in country.
     * @return list name operator in country.
     */
    public List<String> getOperatorNameList() {
        return operatorNameList;
    }
}
