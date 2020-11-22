package com.sms_activate.activation.get_prices;

import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SMSActivateGetPricesResponse {
  /**
   * Map prices where key is country id and short name service.
   */
  private final Map<Integer, Map<String, SMSActivateGetPriceResponse>> smsActivateGetPriceMap;


  /**
   * Constructor response getPrices with data..
   *
   * @param smsActivateGetPriceMap map where first key is countryId, second key is service short name.
   */
  public SMSActivateGetPricesResponse(@NotNull Map<Integer, Map<String, SMSActivateGetPriceResponse>> smsActivateGetPriceMap) {
    this.smsActivateGetPriceMap = smsActivateGetPriceMap;
  }

  /**
   * Returns the object with info about service.
   *
   * @param countryId   specified country id.
   * @param serviceName service short name.
   * @return object with info about service.
   */
  @Nullable
  public SMSActivateGetPriceResponse get(int countryId, @NotNull String serviceName) {
    return getPricesByCountry(countryId).get(serviceName);
  }

  /**
   * Returns the map services with info by country.
   *
   * @param countryId country id.
   * @return map services with info by country.
   */
  @Nullable
  public Map<String, SMSActivateGetPriceResponse> getPricesByCountry(int countryId) {
    return this.smsActivateGetPriceMap.get(countryId);
  }

  /**
   * Returns the map with countryId and service map.
   * <ul>
   *  <li>
   *    key first map is country id.
   *  </li>
   *  <li>
   *    key second map is service short name.
   *  </li>
   * </ul>
   *
   * @return map with countryId and service map.
   */
  @NotNull
  public Map<Integer, Map<String, SMSActivateGetPriceResponse>> getSmsActivateGetPriceMap() {
    return smsActivateGetPriceMap;
  }
}
