package com.sms_activate.activation.get_countries;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetCountriesResponse {
  /**
   * Countries list.
   */
  private final List<SMSActivateGetCountryResponse> smsActivateGetCountryResponseList;

  /**
   * Constructor getCountries response.
   *
   * @param smsActivateGetCountryResponseList list countries in sms-activate DB.
   */
  public SMSActivateGetCountriesResponse(@NotNull List<SMSActivateGetCountryResponse> smsActivateGetCountryResponseList) {
    this.smsActivateGetCountryResponseList = smsActivateGetCountryResponseList;
  }

  /**
   * Returns the country info by id.
   *
   * @param id country id.
   * @return country info.
   */
  @NotNull
  public SMSActivateGetCountryResponse get(int id) {
    return this.smsActivateGetCountryResponseList.get(id);
  }

  /**
   * Returns the countries list.
   *
   * @return countries list.
   */
  @NotNull
  public List<SMSActivateGetCountryResponse> getSMSActivateGetCountryResponseList() {
    return this.smsActivateGetCountryResponseList;
  }
}
