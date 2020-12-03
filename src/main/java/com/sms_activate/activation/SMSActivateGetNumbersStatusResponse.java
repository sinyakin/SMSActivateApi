package com.sms_activate.activation;

import com.sms_activate.activation.extra.SMSActivateServiceInfo;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SMSActivateGetNumbersStatusResponse {
  /**
   * Map services where key is short name service.
   */
  private final Map<String, Integer> smsActivateGetNumberStatusResponseMap;

  /**
   * Constructor response getNumbersStatus with services.
   *
   * @param smsActivateGetNumberStatusResponseMap map services where key is short name service.
   */
  public SMSActivateGetNumbersStatusResponse(@NotNull Map<String, Integer> smsActivateGetNumberStatusResponseMap) {
    this.smsActivateGetNumberStatusResponseMap = smsActivateGetNumberStatusResponseMap;
  }

  /**
   * Returns the service by shortName.
   *
   * @param serviceName service short name.
   * @return if serviceName not contains in map then returns null else service object.
   */
  @NotNull
  public SMSActivateServiceInfo get(@NotNull String serviceName) throws SMSActivateWrongParameterException {
    Integer countNumber = smsActivateGetNumberStatusResponseMap.get(serviceName);

    if (countNumber == null) {
      throw new SMSActivateWrongParameterException(SMSActivateWrongParameter.WRONG_SERVICE);
    }

    return new SMSActivateServiceInfo(
      serviceName.contains("1"),
      countNumber,
      serviceName
    );
  }

  /**
   * Returns the service name set (name_1 - 1 is forward.).
   *
   * @return service name set (name_1 - 1 is forward.).
   */
  @NotNull
  public Set<String> getServiceNameSet() {
    return smsActivateGetNumberStatusResponseMap.keySet();
  }

  /**
   * Returns the all services.
   *
   * @return all services.
   */
  @NotNull
  public List<SMSActivateServiceInfo> getSMSActivateGetNumberStatusResponseList() {
    List<SMSActivateServiceInfo> smsActivateServiceInfoList = new ArrayList<>();

    for (Map.Entry<String, Integer> entry : smsActivateGetNumberStatusResponseMap.entrySet()) {
      String serviceName = entry.getKey();

      smsActivateServiceInfoList.add(new SMSActivateServiceInfo(
        serviceName.contains("1"),
        entry.getValue(),
        serviceName
      ));
    }

    return smsActivateServiceInfoList;
  }
}
