package com.sms_activate.country;

import com.sms_activate.service.ServiceWithCost;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ServiceWithCountry {
  /**
   * Information about country.
   */
  private final Country country;

  /**
   * List services in country.
   */
  private final List<ServiceWithCost> serviceWithCostList;

  /**
   * Constructor country with information and list service.
   *
   * @param country  information about country.
   * @param serviceWithCostList list services in country.
   */
  public ServiceWithCountry(@NotNull Country country, @NotNull List<ServiceWithCost> serviceWithCostList) {
    this.country = country;
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
  public Country getCountryInformation() {
    return country;
  }
}
