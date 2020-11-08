package com.sms_activate.rent;

import com.sms_activate.country.Country;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 */
public class Rent {
    /**
     *
     */
    private final Country country;

    /**
     *
     */
    private final List<Integer> countryIdList;

    /**
     * List operator name.
     */
    private final List<String> operatorNameList;

    /**
     * Constructor rent with operatorNameList, countryList.
     * @param operatorNameList list name operator in country.
     * @param country
     * @param countryIdList
     */
    public Rent(@NotNull List<String> operatorNameList, @NotNull Country country, @NotNull List<Integer> countryIdList) {
       this.operatorNameList = operatorNameList;
       this.country = country;
       this.countryIdList = countryIdList;
    }

    /**
     *
     * @return
     */
    public Country getCountry() {
        return country;
    }

    /**
     *
     * @return
     */
    public List<Integer> getCountryIdList() {
        return countryIdList;
    }

    /**
     * Returns the list name operator in country.
     * @return
     */
    public List<String> getOperatorNameList() {
        return operatorNameList;
    }
}
