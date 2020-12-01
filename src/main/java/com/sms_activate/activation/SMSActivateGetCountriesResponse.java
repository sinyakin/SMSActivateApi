package com.sms_activate.activation;

import com.sms_activate.activation.extra.SMSActivateCountryInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetCountriesResponse {
  /**
   * Countries list.
   */
  private final List<SMSActivateCountryInfo> smsActivateCountryInfoList;

  /**
   * Constructor getCountries response.
   *
   * @param smsActivateCountryInfoList list countries in sms-activate DB.
   */
  public SMSActivateGetCountriesResponse(@NotNull List<SMSActivateCountryInfo> smsActivateCountryInfoList) {
    this.smsActivateCountryInfoList = smsActivateCountryInfoList;
  }

  /**
   * Returns the country info by id.
   *
   * @param id country id.
   * @return country info.
   */
  @NotNull
  public SMSActivateCountryInfo get(int id) {
    return this.smsActivateCountryInfoList.get(id);
  }

  /**
   * Returns the countries list.
   *
   * @return countries list.
   */
  @NotNull
  public List<SMSActivateCountryInfo> getSMSActivateGetCountryResponseList() {
    return this.smsActivateCountryInfoList;
  }
}
