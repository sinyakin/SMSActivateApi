package com.sms_activate.rent;

import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.extra.SMSActivateRentNumber;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMSActivateGetRentListResponse {
  /**
   *  Map current rents where key is id rent.
   */
  private final Map<Integer, SMSActivateRentNumber> smsActivateRentNumberMap;

  /**
   * Constructor response getRentList with list current rents.
   * @param smsActivateRentNumberList list current rents.
   */
  public SMSActivateGetRentListResponse(@NotNull Map<Integer, SMSActivateRentNumber> smsActivateRentNumberList) {
    this.smsActivateRentNumberMap = smsActivateRentNumberList;
  }

  /**
   * Returns the rent by index.
   * @param id index rent.
   * @return rent object.
   * @throws SMSActivateWrongParameter if id incorrect.
   */
  @NotNull
  public SMSActivateRentNumber get(int id) throws SMSActivateWrongParameterException {
    SMSActivateRentNumber smsActivateRentNumber = this.smsActivateRentNumberMap.get(id);

    if (smsActivateRentNumber == null) {
      throw new SMSActivateWrongParameterException(SMSActivateWrongParameter.INVALID_PHONE);
    }

    return smsActivateRentNumber;
  }

  /**
   * Returns the list current rents.
   * @return the list current rents.
   */
  @NotNull
  public List<SMSActivateRentNumber> getSmsActivateGetRentResponseList() {
    return new ArrayList<>(smsActivateRentNumberMap.values());
  }
}
