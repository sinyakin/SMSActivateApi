package com.sms_activate.country;

import com.sms_activate.service.ServiceWithCost;
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
  private final List<ServiceWithCost> serviceWithCostList;

  /**
   * Constructor country with information and list service.
   *
   * @param countryInformation  information about country.
   * @param serviceWithCostList list services in country.
   */
  public Country(@NotNull CountryInformation countryInformation, @NotNull List<ServiceWithCost> serviceWithCostList) {
    this.countryInformation = countryInformation;
    this.serviceWithCostList = serviceWithCostList;
  }

  /**
   * Returns the list services.
   *
   * @return list services.
   */
  @NotNull
  public List<ServiceWithCost> getServiceCostList() {
    return serviceWithCostList;
  }

  /**
   * Returns the information about country.
   *
   * @return information about country.
   */
  @NotNull
  public CountryInformation getCountryInformation() {
    return countryInformation;
  }
}
