package com.sms_activate.rent;

import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.extra.SMSActivateRentNumber;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SMSActivateGetRentListResponse {
  /**
   * Map current rents where key is id rent.
   */
  private Map<Integer, SMSActivateRentNumber> values;

  private SMSActivateGetRentListResponse() {
  }

  /**
   * Returns the rent by index.
   *
   * @param index index rent.
   * @return rent object.
   * @throws SMSActivateWrongParameter if index incorrect.
   */
  @NotNull
  public SMSActivateRentNumber get(int index) throws SMSActivateWrongParameterException {
    SMSActivateRentNumber smsActivateRentNumber = this.values.get(index);

    if (smsActivateRentNumber == null) {
      throw new SMSActivateWrongParameterException(SMSActivateWrongParameter.INVALID_PHONE);
    }

    return smsActivateRentNumber;
  }

  /**
   * Returns the all index your rent.
   *
   * @return all index your rent.
   */
  @NotNull
  public SortedSet<Integer> getIndexSet() {
    return new TreeSet<>(this.values.keySet());
  }

  /**
   * Returns the list current rents.
   *
   * @return the list current rents.
   */
  @NotNull
  public List<SMSActivateRentNumber> getSmsActivateGetRentResponseList() {
    return new ArrayList<>(values.values());
  }
}
