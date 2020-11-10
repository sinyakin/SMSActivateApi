package com.sms_activate.rent;

import com.sms_activate.country.ServiceWithCountry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Rent {
  /**
   * Country current phone activation.
   */
  private final ServiceWithCountry serviceWithCountry;

  /**
   * List countriesId supports activation.
   */
  private final List<Integer> countryIdList;

  /**
   * List operator name.
   */
  private final List<String> operatorNameList;

  /**
   * Constructor rent with operatorNameList, countryList.
   *
   * @param operatorNameList list name operator in country.
   * @param serviceWithCountry          country rent phone.
   * @param countryIdList    list countries supports activation.
   */
  public Rent(@NotNull List<String> operatorNameList, @NotNull ServiceWithCountry serviceWithCountry, @NotNull List<Integer> countryIdList) {
    this.operatorNameList = operatorNameList;
    this.serviceWithCountry = serviceWithCountry;
    this.countryIdList = countryIdList;
  }

  /**
   * Returns the country rent phone.
   *
   * @return country rent phone.
   */
  @NotNull
  public ServiceWithCountry getCountry() {
    return serviceWithCountry;
  }

  /**
   * Returns the list countries supports activation.
   *
   * @return list countries supports activation.
   */
  @NotNull
  public List<Integer> getCountryIdList() {
    return countryIdList;
  }

  /**
   * Returns the list name operator in country.
   *
   * @return list name operator in country.
   */
  @NotNull
  public List<String> getOperatorNameList() {
    return operatorNameList;
  }
}
