package com.sms_activate.activation;

import com.sms_activate.activation.extra.SMSActivateGetPriceInfo;
import com.sms_activate.error.base.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class SMSActivateGetPricesResponse {
  /**
   * Map prices where key is country id and short name service.
   */
  private Map<String, SMSActivateGetPriceInfo> smsActivateGetPriceMap;

  /**
   * Constructor response getPrices with data from server.
   *
   * @param smsActivateGetPriceMap map where first key is countryId, second key is service short name.
   */
  public SMSActivateGetPricesResponse(@NotNull Map<String, SMSActivateGetPriceInfo> smsActivateGetPriceMap) {
    this.smsActivateGetPriceMap = smsActivateGetPriceMap;
  }

  /**
   * Returns the object with info about service.
   *
   * @param countryId   specified country id.
   * @param serviceName service short name.
   * @return object with info about service.
   */
  @NotNull
  public SMSActivateGetPriceInfo get(@NotNull String serviceName) throws SMSActivateBaseException {
    SMSActivateGetPriceInfo smsActivateGetPriceInfo = this.smsActivateGetPriceMap.get(serviceName);

    if (smsActivateGetPriceInfo == null) {
      throw new SMSActivateBaseException("Wrong service name.", "Некорректное имя сервиса.");
    }

    return smsActivateGetPriceInfo;
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
  /*@NotNull
  public Map<Integer, Map<String, SMSActivateGetPriceInfo>> getSmsActivateGetPriceMap() {
    return smsActivateGetPriceMap;
  }*/
}
